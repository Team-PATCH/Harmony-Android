package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable

@Composable
internal fun OnboardingRoute(
    onKakaoLoginRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
    onStartScreenRequest: () -> Unit,
) {
    OnboardingFirstScreen(
        onKakaoLoginRequest = onKakaoLoginRequest,
        onPermissionNotificationRequest = onPermissionNotificationRequest,
        onStartScreenRequest = onStartScreenRequest
    )
}

@Composable
internal fun OnboardingStartRoute(
    onMakeGroupRequest: () -> Unit,
    onEnterScreenRequest: () -> Unit,
) {
    OnboardingStartScreen(
        onboardingMakeGroupRequest = onMakeGroupRequest,
        onboardingEnterScreenRequest = onEnterScreenRequest
    )
}

@Composable
internal fun OnboardingInvitationRoute(
    onNextClick: () -> Unit,
) {
    OnboardingEnterScreen(
        onNextClick = onNextClick
    )
}