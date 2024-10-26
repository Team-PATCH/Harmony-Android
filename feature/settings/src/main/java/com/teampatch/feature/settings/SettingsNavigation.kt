package com.teampatch.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettingsScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator. Extras? = null
) {
    navigate(SETTINGS_ROUTE, navOptions, navigatorExtras)
}

/**
 * @param onTosClick  Terms of service
 */

fun NavGraphBuilder.addSettingsScreen(
    onBackRequest: () -> Unit,
    onExitAppRequest: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTosClick: () -> Unit,
) {
    composable(SETTINGS_ROUTE) {
        SettingsRoute(
            onBackRequest = onBackRequest,
            onExitAppRequest = onExitAppRequest,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onTosClick = onTosClick
        )
    }
}