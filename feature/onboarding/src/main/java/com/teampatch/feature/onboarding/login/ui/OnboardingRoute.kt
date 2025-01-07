package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun OnboardingRoute(
    onKakaoLoginRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
    onStartScreenRequest: () -> Unit,

    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
) {
    // 현재 Context 가져오기
    val context = LocalContext.current
    OnboardingFirstScreen(
        onKakaoLoginRequest = onKakaoLoginRequest,
        onPermissionNotificationRequest = onPermissionNotificationRequest,
        onStartScreenRequest = onStartScreenRequest
    )
}