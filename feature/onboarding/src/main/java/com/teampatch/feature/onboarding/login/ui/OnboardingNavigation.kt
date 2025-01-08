package com.teampatch.feature.onboarding.login.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * 1. OnboardingRoute
 * 2. 파라미터가 되는 OnboardingPermissionRoute, OnboardingStartRoute
 * 3. StartScreen의 파라미터가 되는 OnboardingMakeGroupRoute, OnboardingEnterRoute
 */
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
    onboardingMakeGroupRequest : () -> Unit,
    onboardingEnterScreenRequest: () -> Unit
) {
    composable<OnboardingStartRoute> {
        OnboardingStartRoute(
            onboardingMakeGroupRequest = onboardingMakeGroupRequest,
            onboardingEnterScreenRequest = onboardingEnterScreenRequest
        )
    }
}

@Serializable
data object  OnboardingMakeGroupRoute
fun NavController.navigateToMakeGroupScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeGroupRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeGroupScreen(
) {
    composable<OnboardingMakeGroupRoute> {
        OnboardingMakeGroupScreen()
    }
}

@Serializable
data object  OnboardingEnterRoute
fun NavController.navigateToEnterScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingEnterRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingEnterScreen(
) {
    composable<OnboardingEnterRoute> {
        OnboardingEnterScreen()
    }
}





