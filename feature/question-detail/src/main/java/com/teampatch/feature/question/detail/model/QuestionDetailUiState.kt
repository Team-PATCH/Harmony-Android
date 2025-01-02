package com.teampatch.feature.question.detail.model

import androidx.compose.runtime.MutableState
import androidx.paging.PagingData
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal data class QuestionDetailUiState(
    val post: Post = Post("", 0, "", "", LocalDateTime.now(), 0, false),
    val comments: Flow<PagingData<Comment>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = true,
) {

    data class Post(
        val id: String,
        val number: Int,
        val title: String,
        val content: String,
        val dateTime: LocalDateTime,
        val commentCount: Int,
        val hasWritePermission: Boolean,
    )

    data class Comment(
        val id: String,
        val content: String,
        val writer: Writer,
        val hasWritePermission: Boolean,
        var isCommentEdited: MutableState<Boolean>,
    ) {
        data class Writer(
            val uid: String,
            val name: String,
        )
    }
}