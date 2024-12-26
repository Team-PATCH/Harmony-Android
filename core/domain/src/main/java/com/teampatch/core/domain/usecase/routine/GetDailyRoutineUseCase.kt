package com.teampatch.core.domain.usecase.routine

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Todo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetDailyRoutineUseCase @Inject constructor() {

    operator fun invoke(): Flow<PagingData<Todo>> = emptyFlow()
}