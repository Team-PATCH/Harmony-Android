package com.teampatch.feature.memorystorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.memory.GetLatestMemoryCardUseCase
import com.teampatch.core.domain.usecase.memory.GetMemoryCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MemoryStorageViewModel @Inject constructor(
    private val getLatestMemoryCardUseCase: GetLatestMemoryCardUseCase,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
) : ViewModel() {

    private val _event: Channel<MemoryStorageEvent> = Channel()
    val event: Flow<MemoryStorageEvent> = _event.receiveAsFlow()

    private val _memoryStorageUiState = MutableStateFlow(MemoryStorageUiState())
    val memoryStorageUiState = _memoryStorageUiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
//        try {
//            val memoryCard = getMemoryCardUseCase("somdId")
//            _memoryStorageUiState.value = MemoryStorageUiState(
//
//            )
//        }
    }
}