package com.teampatch.core.domain.usecase.question

import javax.inject.Inject

class EditQuestionCommentUseCase @Inject constructor() {

    suspend operator fun invoke(
        commentId: String,
        comment: String
    ) {

    }
}