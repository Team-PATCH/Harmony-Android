package com.teampatch.core.domain.usecase.daily

import com.teampatch.core.domain.fake.FakeDailyRepository
import com.teampatch.core.domain.model.Todo
import javax.inject.Inject

class AddDailyRoutineUseCase @Inject constructor(
    private val repository: FakeDailyRepository,
) {
    suspend operator fun invoke(todo: Todo): Result<Unit> = repository.addDaily(todo)
}