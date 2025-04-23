package com.teampatch.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.harmony.core.database.dao.TodoDao
import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.repository.TodoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TodoOfflineRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {

    override fun getAllTodos(): Flow<PagingData<Todo>> = todoDao.getAllTodos().map { todos ->
        PagingData.from(todos).map {
            it.toDomain()
        }
    }
}