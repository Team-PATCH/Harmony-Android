package com.teampatch.feature.memorystorage

import com.teampatch.core.domain.model.MemoryCard

sealed class MemoryStorageUiState {
    object Loading : MemoryStorageUiState()
    data class Success(val memories: Map<String, MemoryCard>) : MemoryStorageUiState()
    data class Error(val message: String) : MemoryStorageUiState()
}