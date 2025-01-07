package com.teampatch.feature.onboarding.login.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

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
    onStartScreenRequest: () -> Unit
) {
    composable<OnboardingRoute> {
        OnboardingRoute(
            onKakaoLoginRequest = onKakaoLoginRequest,
            onPermissionNotificationRequest = onPermissionNotificationRequest,
            onStartScreenRequest = onStartScreenRequest
        )
    }
}

@Serializable
data object OnboardingPermissionRoute
fun NavController.navigateToPermissionNotificationScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingPermissionRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingPermissionNotificationScreen(
) {
    composable<OnboardingPermissionRoute> {
        OnboardingPermissionNotificationScreen()
    }
}

@Serializable
data object OnboardingStartRoute
fun NavController.navigateToStartScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingStartRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingStartScreen(
) {
    composable<OnboardingStartRoute> {
        OnboardingStartScreen(
            onboardingMakeGroupRequest = {},
            onboardingEnterScreen = {}
        )
    }
}



