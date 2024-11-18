package com.example.daily_manage.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Daily
import com.teampatch.core.domain.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class DailyManageUiState(
    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val isLoading: Boolean = true
)