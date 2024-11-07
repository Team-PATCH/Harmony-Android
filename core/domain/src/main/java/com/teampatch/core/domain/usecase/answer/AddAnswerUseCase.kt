package com.teampatch.core.domain.usecase.answer

import javax.inject.Inject

class AddAnswerUseCase @Inject constructor() {

    suspend operator fun invoke(questionId: String, answer: String) {

    }
}