package com.teampatch.core.domain.usecase.daily

import com.teampatch.core.domain.fake.FakeDailyManage
import com.teampatch.core.domain.fake.FakeQuestionDetail
import com.teampatch.core.domain.model.DailyManage
import com.teampatch.core.domain.model.QuestionDetail
import javax.inject.Inject

class GetDailyManageUseCase @Inject constructor() {

    operator fun invoke(dailyId: String): DailyManage = FakeDailyManage().get()
}