package com.teampatch.harmony

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DailyRoute

fun NavController.navigateToDailyScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyScreen(
    dailyExpandPageRequest: () -> Unit,
    dailyEditPageRequest: () -> Unit,
) {
    composable<DailyRoute> {
        DailyRoute(
            dailyExpandPageRequest = dailyExpandPageRequest,
            dailyEditPageRequest = dailyEditPageRequest
        )
    }
}