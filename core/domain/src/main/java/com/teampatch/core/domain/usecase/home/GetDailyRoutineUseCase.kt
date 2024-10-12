package com.teampatch.core.domain.usecase.home

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface GetDailyRoutineUseCase {

    operator fun invoke(): Flow<PagingData<Todo>>
}