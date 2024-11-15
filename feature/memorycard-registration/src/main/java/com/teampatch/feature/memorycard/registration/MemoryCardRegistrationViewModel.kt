package com.teampatch.feature.memorycard.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.common.exception.PermissionDeniedException
import com.teampatch.core.domain.usecase.memory.AddMemoryCardRecordingUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardQuestionUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationSideEffect
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationUiState
import com.teampatch.feature.memorycard.registration.model.RecordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MemoryCardRegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addMemoryCardRecordingUseCase: AddMemoryCardRecordingUseCase,
    private val getMemoryCardQuestionUseCase: GetMemoryCardQuestionUseCase,
    private val getMemoryCardUseCase: GetMemoryCardUseCase
) : ViewModel() {

    private val _sideEffect: Channel<MemoryCardRegistrationSideEffect> = Channel()
    val sideEffect: Flow<MemoryCardRegistrationSideEffect> = _sideEffect.receiveAsFlow()

    private val route: MemoryCardRegistrationRoute? = kotlin.runCatching {
        savedStateHandle.toRoute<MemoryCardRegistrationRoute>()
    }
        .onFailure {
            _sideEffect.trySend(MemoryCardRegistrationSideEffect.LoadError)
            it.printStackTrace()
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
            _sideEffect.send(MemoryCardRegistrationSideEffect.LoadError)
            e.printStackTrace()
        }
    }

    fun startRecord() = viewModelScope.launch {
        try {
            uiState = uiState.copy(recordState = RecordState.RECORDING)

            val question = getMemoryCardQuestionUseCase(route!!.memoryCardId)
            uiState = uiState.copy(questions = listOf(question.question))

            addMemoryCardRecordingUseCase(
                memoryCardId = route.memoryCardId,
                question = question.question,
                isRecordFinished = { uiState.recordState == RecordState.COMPLETE }
            )
        } catch (e: Exception) {
            if (e is PermissionDeniedException) {
                _sideEffect.send(MemoryCardRegistrationSideEffect.RecordingPermissionDeniedError)
            } else {
                _sideEffect.send(MemoryCardRegistrationSideEffect.RecordingError)
            }

            uiState = uiState.copy(recordState = RecordState.INIT)
            e.printStackTrace()
        }
    }

    fun stopRecord() {
        uiState = uiState.copy(recordState = RecordState.COMPLETE)
    }
}