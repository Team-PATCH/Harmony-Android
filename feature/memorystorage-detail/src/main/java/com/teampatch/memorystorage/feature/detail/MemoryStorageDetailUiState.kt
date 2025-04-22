package com.teampatch.memorystorage.feature.detail

import com.teampatch.core.domain.model.MemoryCard

sealed class MemoryStorageDetailUiState {
    object Loading : MemoryStorageDetailUiState()
    data class Success(val memories: Map<String, MemoryCard>) : MemoryStorageDetailUiState()
    data class Error(val message: String) : MemoryStorageDetailUiState()
}