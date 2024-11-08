package com.teampatch.core.domain.usecase.answer

import javax.inject.Inject

class EditAnswerUseCase @Inject constructor() {

    suspend operator fun invoke(
        questionId: String,
        answer: String
    ) {

    }
}