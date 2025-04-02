package com.teampatch.feature.daily.expand.model

import com.teampatch.core.domain.model.DailyManage

internal data class DailyExpandUiState(
    val dailyManage: DailyManage? = null, // 기본값을 null로 설정
    val isLoading: Boolean = true,
)