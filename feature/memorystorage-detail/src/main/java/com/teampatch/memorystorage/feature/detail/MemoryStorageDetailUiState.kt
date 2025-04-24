package com.teampatch.memorystorage.feature.detail

import com.teampatch.core.domain.model.MemoryCard

enum class MemoryStorageDetailScreenState {
    Detail,
    Conversation,
}

sealed class MemoryStorageDetailUiState(
    open val screenState: MemoryStorageDetailScreenState = MemoryStorageDetailScreenState.Detail,
) {
    data class Loading(
        override val screenState: MemoryStorageDetailScreenState = MemoryStorageDetailScreenState.Detail,
    ) : MemoryStorageDetailUiState()

    data class Success(
        val memoryCard: MemoryCard,
        override val screenState: MemoryStorageDetailScreenState = MemoryStorageDetailScreenState.Detail,
    ) : MemoryStorageDetailUiState()

    data class Error(
        val message: String,
        override val screenState: MemoryStorageDetailScreenState = MemoryStorageDetailScreenState.Detail,
    ) : MemoryStorageDetailUiState()
}