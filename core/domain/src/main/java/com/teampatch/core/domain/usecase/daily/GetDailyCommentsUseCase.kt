package com.teampatch.core.domain.usecase.daily

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeDailyComments
import com.teampatch.core.domain.fake.FakeQuestionComments
import com.teampatch.core.domain.model.DailyComment
import com.teampatch.core.domain.model.QuestionComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetDailyCommentsUseCase @Inject constructor() {

    operator fun invoke(dailyId: String): Flow<PagingData<DailyComment>> {
        return flowOf(PagingData.from(FakeDailyComments().get()))
    }
}