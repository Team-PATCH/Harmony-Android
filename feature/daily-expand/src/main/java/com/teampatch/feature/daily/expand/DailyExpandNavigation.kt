package com.teampatch.feature.daily.expand

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DailyExpandRoute

fun NavController.navigateToDailyExpandScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyExpandRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyExpandScreen(
    onBackRequest: () -> Unit,
    dailyEditPageRequest: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    composable<DailyExpandRoute> {
        DailyExpandRoute(
            onBackRequest = onBackRequest,
            onEditDailyRequest = dailyEditPageRequest,
            onDeleteDailyRequest = onDeleteClick
        )
    }
}