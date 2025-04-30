package com.teampatch.core.domain.usecase.daily

import javax.inject.Inject

class ToggleDailyRoutineStatusUseCase @Inject constructor() {

    suspend operator fun invoke(
        id: String,
        isFinished: Boolean,
    ) {
    }
}