package com.teampatch.core.domain.fake

import com.teampatch.core.domain.model.DailyManage
import java.time.LocalDateTime

class FakeDailyManage : FakeModel<DailyManage>() {

    override fun build(): DailyManage = DailyManage(
        id = "qd001",
        number = 1,
        title = "What inspired you to start coding?",
        content = "I've always been fascinated by technology and how things work. Coding allows me to create and solve problems in unique ways.",
        dateTime = LocalDateTime.now(),
        commentCount = 100
    )
}