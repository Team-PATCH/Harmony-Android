package com.teampatch.feature.question.detail.model

import androidx.paging.PagingData
import com.teampatch.core.domain.model.QuestionComment
import com.teampatch.core.domain.model.QuestionDetail
import com.teampatch.core.domain.model.User
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class QuestionDetailUiState(
    val user: User = User.init(),
    val detail: QuestionDetail = QuestionDetail("", 0, "", "", LocalDateTime.now(), 0),
    val comment: Flow<PagingData<QuestionComment>> = emptyFlow(),
    val isLoading: Boolean = true,
)