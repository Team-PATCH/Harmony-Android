package com.teampatch.core.domain.usecase.daily

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeDaily
import com.teampatch.core.domain.fake.FakeQuestions
import com.teampatch.core.domain.model.Daily
import com.teampatch.core.domain.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetDailyUseCase @Inject constructor() {

    operator fun invoke(limit: Int = 5): Flow<PagingData<Daily>> {
        return flowOf(PagingData.from(FakeDaily().get()))
    }
}