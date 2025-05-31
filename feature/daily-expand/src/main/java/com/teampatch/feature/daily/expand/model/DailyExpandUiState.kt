package com.teampatch.feature.daily.expand.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DailyExpandUiState(
    val dailyRoutine: Flow<PagingData<Todo>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
)