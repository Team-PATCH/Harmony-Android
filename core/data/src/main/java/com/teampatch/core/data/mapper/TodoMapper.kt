package com.teampatch.core.data.mapper

import com.harmony.core.database.model.TodoEntity
import com.teampatch.core.domain.model.Todo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun TodoEntity.toDomain() = Todo(
    id = id.toString(),
    dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault()),
    title = title,
    isFinished = isFinished
)