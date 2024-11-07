package com.teampatch.core.domain.fake

import com.teampatch.core.domain.model.QuestionDetail
import java.time.LocalDateTime

class FakeQuestionDetail : FakeModel<QuestionDetail>() {

    override fun build(): QuestionDetail {
        return QuestionDetail(
            id = "qd001",
            number = 1,
            title = "What inspired you to start coding?",
            content = "I've always been fascinated by technology and how things work. Coding allows me to create and solve problems in unique ways.",
            dateTime = LocalDateTime.now(),
            commentCount = 100
        )
    }
}