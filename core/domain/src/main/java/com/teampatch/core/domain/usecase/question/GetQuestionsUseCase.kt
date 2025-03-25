package com.teampatch.core.domain.usecase.question

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeQuestions
import com.teampatch.core.domain.model.Question
import com.teampatch.core.domain.repository.QuestionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private const val QUESTION_LIMIT_COUNT = 3

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
) {

    operator fun invoke(limit: Int = QUESTION_LIMIT_COUNT): Flow<PagingData<Question>> = flowOf(PagingData.from(FakeQuestions().get()))
}