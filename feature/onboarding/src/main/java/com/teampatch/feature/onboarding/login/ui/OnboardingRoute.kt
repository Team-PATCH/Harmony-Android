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
    onBackRequest: () -> Unit,
    onMakeGroupRequest: () -> Unit,
    onEnterScreenRequest: () -> Unit,
) {
    OnboardingStartScreen(
        onBackRequest = onBackRequest,
        onboardingMakeGroupRequest = onMakeGroupRequest,
        onboardingEnterScreenRequest = onEnterScreenRequest
    )
}