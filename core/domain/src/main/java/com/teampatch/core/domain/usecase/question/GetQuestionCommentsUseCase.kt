package com.teampatch.core.domain.usecase.question

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeQuestionComments
import com.teampatch.core.domain.model.QuestionComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetQuestionCommentsUseCase @Inject constructor() {

    operator fun invoke(questionId: String): Flow<PagingData<QuestionComment>> {
        return flowOf(PagingData.from(FakeQuestionComments().get()))
    }
}