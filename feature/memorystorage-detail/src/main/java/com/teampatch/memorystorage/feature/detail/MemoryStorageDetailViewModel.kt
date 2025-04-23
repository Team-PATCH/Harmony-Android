package com.teampatch.memorystorage.feature.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryStorageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
) : ViewModel() {

    private val memoryStorageDetailRoute: MemoryStorageDetailRoute = savedStateHandle.toRoute()
    val memoryCardId: String = memoryStorageDetailRoute.memoryCardId

    private val _memoryStorageDetailUiState = MutableStateFlow<MemoryStorageDetailUiState>(
        MemoryStorageDetailUiState.Loading()
    )

    val memoryStorageDetailUiState = _memoryStorageDetailUiState.asStateFlow()

    private var uiState: MemoryStorageDetailUiState
        get() = _memoryStorageDetailUiState.value
        set(value) {
            _memoryStorageDetailUiState.value = value
        }

    fun showConversation() {
        _memoryStorageDetailUiState.update { current ->
            when (current) {
                is MemoryStorageDetailUiState.Success -> current.copy(screenState = MemoryStorageDetailScreenState.Conversation)
                is MemoryStorageDetailUiState.Error -> current.copy(screenState = MemoryStorageDetailScreenState.Conversation)
                is MemoryStorageDetailUiState.Loading -> current.copy(screenState = MemoryStorageDetailScreenState.Conversation)
            }
        }
    }

    fun showDetail() {
        _memoryStorageDetailUiState.update { current ->
            when (current) {
                is MemoryStorageDetailUiState.Success -> current.copy(screenState = MemoryStorageDetailScreenState.Detail)
                is MemoryStorageDetailUiState.Error -> current.copy(screenState = MemoryStorageDetailScreenState.Detail)
                is MemoryStorageDetailUiState.Loading -> current.copy(screenState = MemoryStorageDetailScreenState.Detail)
            }
        }
    }

    private val _event: Channel<MemoryStorageDetailEvent> = Channel()
    val event: Flow<MemoryStorageDetailEvent> = _event.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _memoryStorageDetailUiState.value = MemoryStorageDetailUiState.Loading()

        try {
            val memoryCard = getMemoryCardUseCase(memoryCardId)
            _memoryStorageDetailUiState.value = MemoryStorageDetailUiState.Success(
                memories = mapOf(memoryCard.id to memoryCard)
            )
        } catch (e: Exception) {
            _memoryStorageDetailUiState.value = MemoryStorageDetailUiState.Error("데이터 로딩 실패")
            _event.send(MemoryStorageDetailEvent.LoadError)
        }
    }
}