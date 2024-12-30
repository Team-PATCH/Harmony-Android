package com.teampatch.core.domain.usecase.daily

import javax.inject.Inject

class EditDailyCommentUseCase @Inject constructor() {

    suspend operator fun invoke(
        commentId: String,
        comment: String,
    ) {
    }
}