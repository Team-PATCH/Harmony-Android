package com.teampatch.feature.memorycard.registration.model

internal data class MemoryCardRegistrationUiState(
    val title: String = "",
    val imageUrl: String? = null,
    val questions: List<String> = emptyList(),
    val questionProgressIndex: Int = 0,
    val isLoading: Boolean = true,
)