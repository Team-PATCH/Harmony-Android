package com.teampatch.feature.question.detail.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.teampatch.core.domain.model.QuestionDetail
import com.teampatch.feature.question.detail.model.QuestionDetailUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun QuestionDetail.toCommentModel(currentUserName: String): Flow<PagingData<QuestionDetailUiState.Comment>> = comment.map { pagingData ->
    pagingData.map {
        QuestionDetailUiState.Comment(
            id = it.commentId,
            content = it.content,
            writer = QuestionDetailUiState.Comment.Writer(
                uid = it.writerUid,
                name = it.writerName
            ),
            hasWritePermission = currentUserName == it.writerName
        )
    }
}