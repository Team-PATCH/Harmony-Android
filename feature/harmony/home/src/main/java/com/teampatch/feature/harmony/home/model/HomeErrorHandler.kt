package com.teampatch.feature.harmony.home.model

sealed interface HomeErrorHandler {

    data class ChangeDailyRoutineError(val t: Throwable) : HomeErrorHandler
}