package com.teampatch.harmony.model

import androidx.paging.PagingData
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class DailyUiState(
    val user: User = User.createEmptyUser(),
    val daily: Flow<PagingData<CheckableData<Todo>>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
)