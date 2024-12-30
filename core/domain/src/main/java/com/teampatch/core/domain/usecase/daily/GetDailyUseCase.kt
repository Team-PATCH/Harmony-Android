package com.teampatch.core.domain.usecase.daily

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeDaily
import com.teampatch.core.domain.model.Daily
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetDailyUseCase @Inject constructor() {

    operator fun invoke(limit: Int = 5): Flow<PagingData<Daily>> = flowOf(PagingData.from(FakeDaily().get()))
}