package com.teampatch.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSettingsScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(SettingsRoute, navOptions, navigatorExtras)
}

/**
 * @param onTosClick Terms of service
 */

fun NavGraphBuilder.addSettingsScreen(
    onBackRequest: () -> Unit,
    onExitAppRequest: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTosClick: () -> Unit,
) {
    composable<SettingsRoute> {
        SettingsRoute(
            onBackRequest = onBackRequest,
            onExitAppRequest = onExitAppRequest,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onTosClick = onTosClick
        )
    }
}