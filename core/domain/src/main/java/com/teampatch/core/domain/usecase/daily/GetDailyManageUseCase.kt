package com.teampatch.core.domain.usecase.daily

import com.teampatch.core.domain.fake.FakeDailyManage
import com.teampatch.core.domain.model.DailyManage
import javax.inject.Inject

class GetDailyManageUseCase @Inject constructor() {

    operator fun invoke(dailyId: String): DailyManage = FakeDailyManage().get()
}