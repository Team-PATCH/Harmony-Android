package com.example.daily_manage.model

internal sealed interface DailyManageSideEffect {

    data class LoadError(val t: Throwable) : DailyManageSideEffect
}