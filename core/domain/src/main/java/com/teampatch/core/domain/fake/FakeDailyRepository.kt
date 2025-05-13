package com.teampatch.core.domain.fake

import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.repository.DailyRepository
import javax.inject.Inject

class FakeDailyRepository @Inject constructor() : DailyRepository {
    override suspend fun addDaily(todo: Todo): Result<Unit> {
        println("새 일과 추가됨: ${todo.title}")
        return Result.success(Unit)
    }
}