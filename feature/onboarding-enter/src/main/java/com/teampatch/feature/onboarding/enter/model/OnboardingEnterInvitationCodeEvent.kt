package com.teampatch.feature.onboarding.enter.model

internal sealed interface OnboardingEnterInvitationCodeEvent {
    data object Success : OnboardingEnterInvitationCodeEvent
    data class Error(val t: Throwable) : OnboardingEnterInvitationCodeEvent
}