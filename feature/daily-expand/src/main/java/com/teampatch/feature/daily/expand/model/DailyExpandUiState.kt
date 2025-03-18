package com.teampatch.feature.daily.expand.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.DailyManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class DailyExpandUiState(
    val dailyManage: Flow<PagingData<DailyManage>> = emptyFlow(),
    val isLoading: Boolean = true,
)