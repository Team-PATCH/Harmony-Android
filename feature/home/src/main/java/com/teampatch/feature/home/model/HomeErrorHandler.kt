package com.teampatch.feature.home.model

sealed interface HomeErrorHandler {

    data class ChangeDailyRoutineError(val t: Throwable) : HomeErrorHandler
}