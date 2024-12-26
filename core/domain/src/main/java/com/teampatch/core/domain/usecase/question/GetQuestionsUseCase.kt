package com.teampatch.core.domain.usecase.question

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeQuestions
import com.teampatch.core.domain.model.Question
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetQuestionsUseCase @Inject constructor() {

    operator fun invoke(limit: Int = 5): Flow<PagingData<Question>> = flowOf(PagingData.from(FakeQuestions().get()))
}