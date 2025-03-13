package com.teampatch.feature.daily.edit

internal sealed interface DailyEditEvent {
    data class LoadError(val t: Throwable) : DailyEditEvent
    data class AddDailyError(val t: Throwable) : DailyEditEvent
}