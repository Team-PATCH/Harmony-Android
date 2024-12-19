package com.teampatch.daily.manage.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Daily
import com.teampatch.core.domain.model.DailyComment
import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class DailyManageUiState(
//    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val user: User = User.init(),
//    val daily: DailyManage = DailyManage("", 0, "", "", LocalDateTime.now(), 0),
    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val comment: Flow<PagingData<DailyComment>> = emptyFlow(),
    val isLoading: Boolean = true
)