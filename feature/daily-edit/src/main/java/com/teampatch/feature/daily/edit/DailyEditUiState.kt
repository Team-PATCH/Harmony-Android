package com.teampatch.feature.daily.edit

import com.teampatch.core.domain.model.DailyManage
import java.time.LocalDateTime

internal data class DailyEditUiState(
    val dailyExpand: DailyManage = DailyManage(
        id = "",
        number = 0,
        title = "",
        content = "",
        dateTime = LocalDateTime.now(),
        commentCount = 0
    ),
    val isLoading: Boolean = true,
)