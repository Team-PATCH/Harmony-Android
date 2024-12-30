package com.teampatch.feature.settings.model

data class SettingsUiState(
    val isLatestVersion: Boolean = true,
    val installedVersion: String = "",
    val isLoading: Boolean = true,
)