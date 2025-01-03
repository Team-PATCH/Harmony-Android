package com.teampatch.feature.question.detail.model

internal sealed interface CommentEvent {
    data class Add(val commentText: String) : CommentEvent
    data class Edit(val comment: QuestionDetailUiState.Comment, val commentText: String) : CommentEvent
    data class Delete(val comment: QuestionDetailUiState.Comment) : CommentEvent
}