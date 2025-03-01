package com.teampatch.feature.daily.expand.model

internal sealed interface DailyExpandSideEffect {

    data class LoadError(val t: Throwable) : DailyExpandSideEffect
}