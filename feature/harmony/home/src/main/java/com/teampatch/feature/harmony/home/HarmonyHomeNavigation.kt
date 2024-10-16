package com.teampatch.feature.harmony.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val HARMONY_HOME_ROUTE = "harmony_home"

fun NavController.navigateToHarmonyHomeScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route = HARMONY_HOME_ROUTE, navOptions = navOptions, navigatorExtras = navigatorExtras)
}


fun NavGraphBuilder.addHarmonyHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineRegisterPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
) {
    composable(HARMONY_HOME_ROUTE) {
        HarmonyHomeRoute(
            onUserPageRequest = onUserPageRequest,
            onDailyRoutineRegisterPageRequest = onDailyRoutineRegisterPageRequest,
            onDailyRoutineClick = onDailyRoutineClick,
            onMemoryCardClick = onMemoryCardClick
        )
    }
}