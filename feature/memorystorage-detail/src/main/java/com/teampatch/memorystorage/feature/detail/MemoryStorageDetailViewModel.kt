package com.teampatch.memorystorage.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryStorageDetailViewModel @Inject constructor(
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MemoryStorageDetailUiState>(
        MemoryStorageDetailUiState.Loading()
    )
    val uiState: StateFlow<MemoryStorageDetailUiState> = _uiState

    private val memoryStorageDetailRoute: MemoryStorageDetailRoute = savedStateHandle.toRoute()

    private val memoryCardId: String = memoryStorageDetailRoute.memoryCardId

    fun showConversation() {
        val currentState = _uiState.value
        if (currentState is MemoryStorageDetailUiState.Success) {
            _uiState.value = currentState.copy(screenState = MemoryStorageDetailScreenState.Conversation)
        }
    }

    fun showDetail() {
        val currentState = _uiState.value
        if (currentState is MemoryStorageDetailUiState.Success) {
            _uiState.value = currentState.copy(screenState = MemoryStorageDetailScreenState.Detail)
        }
    }

    private val _event: Channel<MemoryStorageDetailEvent> = Channel()
    val event: Flow<MemoryStorageDetailEvent> = _event.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _uiState.value = MemoryStorageDetailUiState.Loading()

        try {
            val memoryCard = getMemoryCardUseCase(memoryCardId)
            _uiState.value = MemoryStorageDetailUiState.Success(memoryCard)
        } catch (e: Exception) {
            _uiState.value = MemoryStorageDetailUiState.Error("카드 로딩 실패")
            _event.send(MemoryStorageDetailEvent.LoadError)
        }
    }
}