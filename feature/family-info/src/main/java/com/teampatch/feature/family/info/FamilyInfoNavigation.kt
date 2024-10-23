package com.teampatch.feature.family.info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val FAMILY_INFO_ROUTE = "family_info"

fun NavController.navigateToFamilyInfoScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(
        route = FAMILY_INFO_ROUTE,
        navOptions = navOptions,
        navigatorExtras = navigatorExtras
    )
}

fun NavGraphBuilder.addFamilyInfoScreen(
    onBackRequest: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileEditClick: () -> Unit,
) {
    composable(FAMILY_INFO_ROUTE) {
        FamilyInfoRoute(
            onBackRequest = onBackRequest,
            onSettingsClick = onSettingsClick,
            onProfileEditClick = onProfileEditClick
        )
    }
}