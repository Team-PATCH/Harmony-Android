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

@Composable
internal fun OnboardingEnterInvitationCodeRoute(
    onNextClick: () -> Unit,
) {
    OnboardingEnterInvitationCodeScreen(
        onNextClick = onNextClick
    )
}

@Composable
internal fun OnboardingEnterSpaceRoute(
    onNextClick: () -> Unit,
) {
    OnboardingEnterSpaceScreen(
        onNextClick = onNextClick
    )
}