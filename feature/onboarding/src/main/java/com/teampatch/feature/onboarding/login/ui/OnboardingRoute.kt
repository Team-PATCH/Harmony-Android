package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable

@Composable
internal fun OnboardingRoute(
    onHomeScreenRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
    onStartScreenRequest: () -> Unit,
) {
    OnboardingFirstScreen(
        onHomeScreenRequest = onHomeScreenRequest,
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