package com.teampatch.feature.daily.expand.model

internal sealed interface DailyExpandEvent {

    data class LoadError(val t: Throwable) : DailyExpandEvent
}