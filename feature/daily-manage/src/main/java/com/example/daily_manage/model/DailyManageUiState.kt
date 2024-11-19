package com.example.daily_manage.model

import androidx.paging.PagingData
import com.teampatch.core.domain.emptyUser
import com.teampatch.core.domain.model.Daily
import com.teampatch.core.domain.model.DailyComment
import com.teampatch.core.domain.model.DailyManage
import com.teampatch.core.domain.model.Question
import com.teampatch.core.domain.model.QuestionComment
import com.teampatch.core.domain.model.QuestionDetail
import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.LocalDateTime

internal data class DailyManageUiState(
//    val daily: Flow<PagingData<Daily>> = emptyFlow(),
    val user: User = emptyUser,
    val daily: DailyManage = DailyManage("", 0, "", "", LocalDateTime.now(), 0),
    val comment: Flow<PagingData<DailyComment>> = emptyFlow(),
    val isLoading: Boolean = true
)