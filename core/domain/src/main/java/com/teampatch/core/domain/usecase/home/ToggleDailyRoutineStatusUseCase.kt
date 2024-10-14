package com.teampatch.core.domain.usecase.home

interface ToggleDailyRoutineStatusUseCase {

    suspend operator fun invoke(
        id: String,
        isFinished: Boolean,
    )
}