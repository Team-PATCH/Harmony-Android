package com.teampatch.core.domain.model

import java.time.LocalDateTime

data class QuestionDetail(
    val id: String,
    val number: Int,
    val title: String,
    val content: String,
    val dateTime: LocalDateTime,
    val commentCount: Int,
)