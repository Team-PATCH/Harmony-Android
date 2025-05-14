package com.teampatch.feature.memorystorage

sealed interface MemoryStorageUiState {
    data object Loading : MemoryStorageUiState

    data object Success : MemoryStorageUiState

    data class Error(val message: String) : MemoryStorageUiState
}