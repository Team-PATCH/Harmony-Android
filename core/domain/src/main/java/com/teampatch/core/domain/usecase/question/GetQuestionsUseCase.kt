package com.teampatch.core.domain.usecase.question

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Question
import com.teampatch.core.domain.repository.QuestionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
) {

    operator fun invoke(limit: Int = 3): Flow<PagingData<Question>> = questionRepository.getQuestions(limit)
}