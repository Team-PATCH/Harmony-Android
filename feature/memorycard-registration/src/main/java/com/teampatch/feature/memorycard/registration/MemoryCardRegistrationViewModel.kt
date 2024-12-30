package com.teampatch.feature.memorycard.registration

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.common.checkRadioAudioPermission
import com.teampatch.core.domain.usecase.memory.AddMemoryCardRecordUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardQuestionUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import com.teampatch.core.domain.usecase.memory.PauseMemoryCardRecordingUseCase
import com.teampatch.core.domain.usecase.memory.ResumeMemoryCardRecordingUseCase
import com.teampatch.core.domain.usecase.memory.StartMemoryCardRecordingUseCase
import com.teampatch.core.domain.usecase.memory.StopMemoryCardRecordingUseCase
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationSideEffect
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationUiState
import com.teampatch.feature.memorycard.registration.model.RecordState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryCardRegistrationViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val savedStateHandle: SavedStateHandle,
    private val startMemoryCardRecordingUseCase: StartMemoryCardRecordingUseCase,
    private val stopMemoryCardRecordingUseCase: StopMemoryCardRecordingUseCase,
    private val resumeMemoryCardRecordingUseCase: ResumeMemoryCardRecordingUseCase,
    private val pauseMemoryCardRecordingUseCase: PauseMemoryCardRecordingUseCase,
    private val addMemoryCardRecordUseCase: AddMemoryCardRecordUseCase,
    private val getMemoryCardQuestionUseCase: GetMemoryCardQuestionUseCase,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
) : ViewModel() {

    private val _sideEffect: Channel<MemoryCardRegistrationSideEffect> = Channel()
    val sideEffect: Flow<MemoryCardRegistrationSideEffect> = _sideEffect.receiveAsFlow()

    private val route: MemoryCardRegistrationRoute? = kotlin.runCatching {
        savedStateHandle.toRoute<MemoryCardRegistrationRoute>()
    }
        .onFailure {
            it.printStackTrace()
            _sideEffect.trySend(MemoryCardRegistrationSideEffect.LoadError)
        }
        .getOrNull()

    var uiState by mutableStateOf(MemoryCardRegistrationUiState())
        private set

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            val memoryCard = getMemoryCardUseCase(route!!.memoryCardId)
            uiState = uiState.copy(
                title = memoryCard.text,
                imageUrl = memoryCard.imageUrl,
                isLoading = false
            )
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(MemoryCardRegistrationSideEffect.LoadError)
        }
    }

    fun startRecord() = viewModelScope.launch {
        try {
            if (!appContext.checkRadioAudioPermission()) {
                _sideEffect.send(MemoryCardRegistrationSideEffect.RecordingPermissionDeniedError)
                return@launch
            }

            uiState = uiState.copy(recordState = RecordState.RECORDING)

            val question = getMemoryCardQuestionUseCase(route!!.memoryCardId)
            uiState = uiState.copy(questions = listOf(question.question))

            startMemoryCardRecordingUseCase()
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(MemoryCardRegistrationSideEffect.RecordingError)
            uiState = uiState.copy(recordState = RecordState.INIT)
        }
    }

    fun stopRecord() = viewModelScope.launch {
        try {
            stopMemoryCardRecordingUseCase()
            addMemoryCardRecordUseCase(route!!.memoryCardId, uiState.questions.first())
            uiState = uiState.copy(recordState = RecordState.COMPLETE)
        } catch (e: Exception) {
            handleRecordingError(e)
        }
    }

    fun resumeRecording() = viewModelScope.launch {
        try {
            resumeMemoryCardRecordingUseCase()
        } catch (e: Exception) {
            handleRecordingError(e)
        }
    }

    fun pauseRecording() = viewModelScope.launch {
        try {
            pauseMemoryCardRecordingUseCase()
        } catch (e: Exception) {
            handleRecordingError(e)
        }
    }

    private suspend fun handleRecordingError(e: Exception) {
        e.printStackTrace()
        uiState = uiState.copy(recordState = RecordState.INIT)
        _sideEffect.send(MemoryCardRegistrationSideEffect.RecordingError)
    }

    override fun onCleared() {
        super.onCleared()
        stopMemoryCardRecordingUseCase()
    }
}