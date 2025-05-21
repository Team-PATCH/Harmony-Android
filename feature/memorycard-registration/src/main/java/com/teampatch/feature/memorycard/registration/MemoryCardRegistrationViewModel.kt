package com.teampatch.feature.memorycard.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.domain.usecase.memory.AddMemoryCardAnswerUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationSideEffect
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryCardRegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
    private val addMemoryCardAnswerUseCase: AddMemoryCardAnswerUseCase,
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

    fun uploadMemoryCardAnswer(answer: String) = viewModelScope.launch {
        try {
            addMemoryCardAnswerUseCase(route!!.memoryCardId, answer)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(MemoryCardRegistrationSideEffect.NetworkError)
        }
    }
}