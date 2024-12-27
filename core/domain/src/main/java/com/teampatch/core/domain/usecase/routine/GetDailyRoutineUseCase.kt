package com.teampatch.core.domain.usecase.routine

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeTodos
import com.teampatch.core.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetDailyRoutineUseCase @Inject constructor() {

    operator fun invoke(): Flow<PagingData<Todo>> {
        return flowOf(PagingData.from(FakeTodos().get()))
    }
}