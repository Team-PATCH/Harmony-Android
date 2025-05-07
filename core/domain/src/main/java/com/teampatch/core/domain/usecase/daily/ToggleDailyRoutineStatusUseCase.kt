package com.teampatch.core.domain.usecase.daily

import com.teampatch.core.domain.repository.TodoRepository
import javax.inject.Inject

class ToggleDailyRoutineStatusUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {

    suspend operator fun invoke(
        id: String,
        isFinished: Boolean,
    ) {
        todoRepository.toggleTodoStatus(id, isFinished)
    }
}