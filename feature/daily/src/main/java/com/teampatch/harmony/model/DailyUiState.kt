package com.teampatch.harmony.model

import androidx.paging.PagingData
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DailyUiState(
    val dailyRoutine: Flow<PagingData<CheckableData<Todo>>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
)