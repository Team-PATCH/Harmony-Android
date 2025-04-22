package com.teampatch.feature.daily.expand

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.teampatch.core.domain.model.DailyManage
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
    dailyEditPageRequest: (DailyManage) -> Unit,
    onDeleteClick: (DailyManage) -> Unit,
) {
    composable<DailyExpandRoute> {
        DailyExpandRoute(
            onBackRequest = onBackRequest,
            onEditDailyRequest = dailyEditPageRequest,
            onDeleteDailyRequest = onDeleteClick
        )
    }
}