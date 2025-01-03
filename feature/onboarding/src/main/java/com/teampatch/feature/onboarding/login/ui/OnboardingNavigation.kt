package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

@Serializable
data object OnboardingRoute

fun NavController.navigateToOnboardingScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingRoute, navOptions, navigatorExtras)
}



fun NavGraphBuilder.addOnboardingScreen(
    onKakaoLoginRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
) {
    composable<OnboardingRoute> {
        OnboardingRoute(
            onKakaoLoginRequest = onKakaoLoginRequest,
            onPermissionNotificationRequest = onPermissionNotificationRequest
        )
    }
}

