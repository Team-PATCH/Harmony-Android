package com.teampatch.core.domain.usecase.routine

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyRoutineUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    operator fun invoke(): Flow<PagingData<Todo>> = todoRepository.getAllTodos()
}