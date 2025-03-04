package com.teampatch.feature.daily.edit

internal sealed interface DailyEditSideEffect {
    data class LoadError(val t: Throwable) : DailyEditSideEffect
    data class AddDailyError(val t: Throwable) : DailyEditSideEffect
}