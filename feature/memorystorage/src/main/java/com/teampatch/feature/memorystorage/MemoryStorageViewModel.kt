package com.teampatch.feature.memorystorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.memory.GetLatestMemoryCardUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryStorageViewModel @Inject constructor(
    private val getLatestMemoryCardUseCase: GetLatestMemoryCardUseCase,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
) : ViewModel() {

    private val _event: Channel<MemoryStorageEvent> = Channel()
    val event: Flow<MemoryStorageEvent> = _event.receiveAsFlow()

    private val _memoryStorageUiState = MutableStateFlow<MemoryStorageUiState>(MemoryStorageUiState.Loading)
    val memoryStorageUiState = _memoryStorageUiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _memoryStorageUiState.value = MemoryStorageUiState.Loading

        try {
            val memoryCard = getMemoryCardUseCase("someId") // 예제 코드
            _memoryStorageUiState.value = MemoryStorageUiState.Success(
                memories = mapOf(memoryCard.id to memoryCard)
            )
        } catch (e: Exception) {
            _memoryStorageUiState.value = MemoryStorageUiState.Error("데이터 로딩 실패")
            _event.send(MemoryStorageEvent.LoadError)
        }
    }
}