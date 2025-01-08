package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

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
    onboardingMakeGroupRequest: () -> Unit,
    onboardingEnterScreenRequest: () -> Unit
) {
    OnboardingStartScreen(
        onboardingMakeGroupRequest = onboardingMakeGroupRequest,
        onboardingEnterScreenRequest = onboardingEnterScreenRequest
    )
}