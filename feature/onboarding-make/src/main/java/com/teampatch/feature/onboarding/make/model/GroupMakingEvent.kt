package com.teampatch.feature.onboarding.make.model

sealed interface GroupMakingEvent {
    data object Success : GroupMakingEvent
    data class Error(val t: Throwable) : GroupMakingEvent
    data object Progress : GroupMakingEvent
}