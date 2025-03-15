package com.teampatch.feature.daily.expand.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.DailyManage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class DailyExpandUiState(
    val dailyManage: Flow<PagingData<DailyManage>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
)