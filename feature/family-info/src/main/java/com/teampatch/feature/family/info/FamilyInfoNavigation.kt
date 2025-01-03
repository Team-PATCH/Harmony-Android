package com.teampatch.feature.family.info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FamilyInfoRoute

fun NavController.navigateToFamilyInfoScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(FamilyInfoRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addFamilyInfoScreen(
    onBackRequest: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileEditClick: () -> Unit,
) {
    composable<FamilyInfoRoute> {
        FamilyInfoRoute(
            onBackRequest = onBackRequest,
            onSettingsClick = onSettingsClick,
            onProfileEditClick = onProfileEditClick
        )
    }
}