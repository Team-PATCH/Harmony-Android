package com.teampatch.memorystorage_detail

data class MemoryStorageDetailUiState(
    val title: String = "",
    val imageUrl: String? = null,
    val questions: List<String> = emptyList(),
    val questionProgressIndex: Int = 0,
    val isLoading: Boolean = true,
)