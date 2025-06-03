package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.Todo

interface DailyRepository {
    suspend fun addDaily(todo: Todo): Result<Unit>
}