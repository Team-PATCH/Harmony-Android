package com.teampatch.feature.daily.edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.teampatch.core.domain.model.Todo
import kotlinx.serialization.Serializable

@Serializable
data object DailyEditRoute

fun NavController.navigateToDailyEditScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyEditRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyEditScreen(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (Todo) -> Unit,
) {
    composable<DailyEditRoute> {
        DailyEditRoute(
            onDismissRequest = onDismissRequest,
            onCompleteRequest = onCompleteRequest
        )
    }
}