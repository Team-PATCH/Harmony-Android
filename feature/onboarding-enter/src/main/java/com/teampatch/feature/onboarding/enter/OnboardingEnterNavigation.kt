package com.teampatch.feature.onboarding.enter

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingEnterInvitationCodeRoute

fun NavController.navigateToEnterInvitationCodeScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingEnterInvitationCodeRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingEnterInvitationCodeScreen(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: () -> Unit,
) {
    composable<OnboardingEnterInvitationCodeRoute> {
        OnboardingEnterInvitationCodeRoute(
            onBackRequest = onBackRequest,
            onEnterRelationScreenRequest = onEnterRelationScreenRequest
        )
    }
}

@Serializable
data object OnboardingEnterRelationRoute

fun NavController.navigateToEnterRelationScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingEnterRelationRoute, navOptions, navigatorExtras)

}

fun NavGraphBuilder.addOnboardingEnterRelationScreen(
    onBackRequest: () -> Unit,
    onEnterSpaceScreenRequest: () -> Unit,
) {
    composable<OnboardingEnterRelationRoute> {
        OnboardingEnterRelationRoute(
            onBackRequest = onBackRequest,
            onEnterSpaceScreenRequest = onEnterSpaceScreenRequest
        )
    }
}


@Serializable
data object OnboardingEnterSpaceRoute

fun NavController.navigateToEnterSpaceScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingEnterSpaceRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingEnterSpaceScreen(
    onBackRequest: () -> Unit,
    onHomeRouteRequest: () -> Unit,
) {
    composable<OnboardingEnterSpaceRoute> {
        OnboardingEnterSpaceScreen(
            onBackRequest = onBackRequest,
            onHomeRouteRequest = onHomeRouteRequest
        )
    }
}