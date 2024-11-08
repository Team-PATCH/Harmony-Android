package com.teampatch.core.domain.usecase.question

import androidx.paging.PagingData
import com.teampatch.core.domain.fake.FakeQuestions
import com.teampatch.core.domain.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor() {

    operator fun invoke(limit: Int = 5): Flow<PagingData<Question>> {
        return flowOf(PagingData.from(FakeQuestions().get()))
    }
}