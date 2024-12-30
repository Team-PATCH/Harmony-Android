package com.teampatch.core.domain.usecase.daily

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeDailyComments
import com.teampatch.core.domain.model.DailyComment
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetDailyCommentsUseCase @Inject constructor() {

    operator fun invoke(dailyId: String): Flow<PagingData<DailyComment>> = flowOf(PagingData.from(FakeDailyComments().get()))
}