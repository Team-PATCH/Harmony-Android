package com.teampatch.feature.onboarding.make

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingMakeGroupRoute

fun NavController.navigateToMakeGroupScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(OnboardingMakeGroupRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addOnboardingMakeParentsNameScreen(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: () -> Unit,
) {
    composable<OnboardingMakeGroupRoute> {
        OnboardingMakeParentsNameScreen(
            onBackRequest = onBackRequest,
            onEnterRelationScreenRequest = onEnterRelationScreenRequest
        )
    }
}

fun NavGraphBuilder.addOnboardingMakeRelationScreen(
    onBackRequest: () -> Unit,
    onInviteGrandParentsScreenRequest: () -> Unit,
) {
    composable<OnboardingMakeGroupRoute> {
        OnboardingMakeRelationScreen(
            onBackRequest = onBackRequest,
            onInviteGrandParentsScreenRequest = onInviteGrandParentsScreenRequest
        )
    }
}