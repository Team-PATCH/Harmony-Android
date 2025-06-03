package com.teampatch.core.domain.model

data class DailyComment(
    val commentId: String,
    val writerUid: String,
    val writerName: String,
    val content: String,
    val imageUrl: String? = null,
    val profileImageUrl: String? = null,
)