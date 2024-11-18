package com.teampatch.harmony.model

internal sealed interface DailySideEffect {

    data class LoadError(val t: Throwable) : DailySideEffect
}