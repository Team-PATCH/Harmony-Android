package com.teampatch.feature.member.home.model

sealed interface HomeErrorHandler {

    data class ChangeDailyRoutineError(val t: Throwable) : HomeErrorHandler

    data class MemoryCardAdditionError(val t: Throwable) : HomeErrorHandler
}