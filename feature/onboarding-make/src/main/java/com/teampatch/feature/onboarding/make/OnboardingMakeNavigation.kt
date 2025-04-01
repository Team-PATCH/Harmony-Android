package com.teampatch.feature.onboarding.make

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingMakeParentsNameRoute

fun NavController.navigateToMakeGroupScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeParentsNameRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeParentsNameScreen(
    onBackRequest: () -> Unit,
    onShareInvitationScreenRequest: () -> Unit,
) {
    composable<OnboardingMakeParentsNameRoute> {
        OnboardingMakeParentsNameScreen(
            onBackRequest = onBackRequest,
            onShareInvitationScreenRequest = onShareInvitationScreenRequest
        )
    }
}

@Serializable
data object OnboardingMakeInvitationRoute

fun NavController.navigateToShareInvitationScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeInvitationRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeInviteGrandParentsScreen(
    onBackRequest: () -> Unit,
    onRelationScreenRequest: () -> Unit,
) {
    composable<OnboardingMakeInvitationRoute> {
        OnboardingMakeInviteCodeCreationScreen(
            onBackRequest = onBackRequest,
            onRelationScreenRequest = onRelationScreenRequest
        )
    }
}

@Serializable
data object OnboardingMakeRelationRoute

fun NavController.navigateToMakeRelationScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeRelationRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeRelationScreen(
    onBackRequest: () -> Unit,
    onProfileSettingsScreenRequest: () -> Unit,
) {
    composable<OnboardingMakeRelationRoute> {
        OnboardingMakeRelationScreen(
            onBackRequest = onBackRequest,
            onProfileSettingsScreenRequest = onProfileSettingsScreenRequest
        )
    }
}

@Serializable
data object OnboardingMakeProfileRoute

fun NavController.navigateToMakeProfileSettingsScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeProfileRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeProfileSettingsScreen(
    onBackRequest: () -> Unit,
    onHomeRouteRequest: () -> Unit,
) {
    composable<OnboardingMakeProfileRoute> {
        OnboardingMakeProfileSettingsScreen(
            onBackRequest = onBackRequest,
            onHomeRouteRequest = onHomeRouteRequest
        )
    }
}