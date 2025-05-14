package com.teampatch.daily.certify

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DailyCertifyAlarmScreenRoute

fun NavController.navigateToDailyCertifyAlarmScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyCertifyAlarmScreenRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyCertifyAlarmScreen(
    onPickImageScreenRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    composable<DailyCertifyAlarmScreenRoute> {
        DailyAlarmRoute(
            onPickImageScreenRequest = onPickImageScreenRequest,
            onDismissRequest = onDismissRequest
        )
    }
}