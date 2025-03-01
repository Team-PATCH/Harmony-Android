package com.teampatch.feature.daily.expand.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Daily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class DailyExpandUiState(
    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val isLoading: Boolean = true,
)