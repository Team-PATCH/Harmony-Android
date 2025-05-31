package com.teampatch.harmony.model

sealed interface DailyErrorHandler {
    data class ChangeRoutineError(val throwable: Throwable) : DailyErrorHandler
}