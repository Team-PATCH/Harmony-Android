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
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationSideEffect
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationUiState
import com.teampatch.feature.memorycard.registration.model.RecordState
import com.teampatch.feature.memorycard.registration.utils.AudioRecorderHelper
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
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
    private val addMemoryCardRecordUseCase: AddMemoryCardRecordUseCase,
    private val audioRecorderHelper: AudioRecorderHelper,
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

    fun startMemoryCardAudioRecord() {
        try {
            if (!appContext.checkRadioAudioPermission()) {
                _sideEffect.trySend(MemoryCardRegistrationSideEffect.RecordingPermissionDeniedError)
                return
            }

            audioRecorderHelper.prepare()
            audioRecorderHelper.start()
            uiState = uiState.copy(recordState = RecordState.RECORDING)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.trySend(MemoryCardRegistrationSideEffect.RecordingError)
        }
    }

    fun stopMemoryCardAudioRecord() {
        try {
            if (uiState.recordState != RecordState.RECORDING) return

            audioRecorderHelper.stop()
            audioRecorderHelper.release()
            uiState = uiState.copy(recordState = RecordState.COMPLETE)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.trySend(MemoryCardRegistrationSideEffect.RecordingError)
        }
    }

    fun uploadMemoryCardAudioRecordFile() = viewModelScope.launch {
        try {
            val contentResolver = appContext.contentResolver
            contentResolver.openInputStream(audioRecorderHelper.getResultRecordFile())!!.use {
                addMemoryCardRecordUseCase(route!!.memoryCardId, uiState.questions.toString(), it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.trySend(MemoryCardRegistrationSideEffect.RecordingError)
        }
    }
}