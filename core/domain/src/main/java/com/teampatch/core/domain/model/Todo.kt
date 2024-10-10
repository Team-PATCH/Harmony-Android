package com.teampatch.core.domain.model

import java.time.LocalDateTime

data class Todo(
    val id: String,
    val dateTime: LocalDateTime,
    val title: String,
    val isFinished: Boolean
)
