package com.teampatch.harmony.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Daily
import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class DailyUiState(
    val user: User = User.init(),
    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val isLoading: Boolean = true,
)