package com.teampatch.feature.question.detail.mapper

import com.teampatch.core.domain.model.QuestionDetail
import com.teampatch.feature.question.detail.model.QuestionDetailUiState

internal fun QuestionDetail.toPostModel(hasWritePermission: Boolean): QuestionDetailUiState.Post = QuestionDetailUiState.Post(
    id = id,
    number = number,
    title = title,
    content = content,
    dateTime = dateTime,
    hasWritePermission = hasWritePermission
)