package com.teampatch.memorystorage.feature.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class MemoryStorageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
) : ViewModel() {

    private val memoryStorageDetailRoute: MemoryStorageDetailRoute = savedStateHandle.toRoute()
    val memoryCardId: String = memoryStorageDetailRoute.memoryCardId

    var uiState by mutableStateOf(MemoryStorageDetailUiState())
        private set

    private val _event: Channel<MemoryStorageDetailEvent> = Channel()
    val event: Flow<MemoryStorageDetailEvent> = _event.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            if (memoryCardId.isEmpty()) {
                Log.d(TAG, "memory_id is null")
                return@launch
            }

            val memoryCard = getMemoryCardUseCase(memoryCardId)

            uiState = MemoryStorageDetailUiState()
        } catch (e: Exception) {
            _event.send(MemoryStorageDetailEvent.LoadError(e))
            e.printStackTrace()
        }
    }
}