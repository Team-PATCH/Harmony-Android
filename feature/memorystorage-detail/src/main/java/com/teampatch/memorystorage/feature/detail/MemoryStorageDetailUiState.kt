package com.teampatch.memorystorage.feature.detail

enum class MemoryDetailScreenState {
    Detail,
    Conversation,
}
data class MemoryStorageDetailUiState(
    val isLoading: Boolean = true,
    val screenState: MemoryDetailScreenState = MemoryDetailScreenState.Detail,
)