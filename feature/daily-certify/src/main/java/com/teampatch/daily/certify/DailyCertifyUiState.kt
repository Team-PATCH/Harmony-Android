package com.teampatch.daily.certify

import java.time.LocalTime

internal data class DailyCertifyUiState(
    val missionText: String = "",
    val missionTime: LocalTime? = null,
    val isCompleted: Boolean = false,
)