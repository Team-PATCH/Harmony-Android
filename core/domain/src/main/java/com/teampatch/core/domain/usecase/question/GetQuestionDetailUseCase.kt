package com.teampatch.core.domain.usecase.question

import com.teampatch.core.domain.fake.FakeQuestionDetail
import com.teampatch.core.domain.model.QuestionDetail
import javax.inject.Inject

class GetQuestionDetailUseCase @Inject constructor() {

    operator fun invoke(questionId: String): QuestionDetail = FakeQuestionDetail().get()
}