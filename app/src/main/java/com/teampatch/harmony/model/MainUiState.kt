package com.teampatch.harmony.model

data class MainUiState(
    val isFirstUser: Boolean = false,
    val isLoginRequired: Boolean = false,
    val isLoading: Boolean = true,
)