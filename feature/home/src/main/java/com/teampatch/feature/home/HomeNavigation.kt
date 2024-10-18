package com.teampatch.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"

fun NavController.navigateToMemberHomeScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route = HOME_ROUTE, navOptions = navOptions, navigatorExtras = navigatorExtras)
}


fun NavGraphBuilder.addMemberHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineRegisterPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
) {
    composable(HOME_ROUTE) {
        HomeRoute(
            onUserPageRequest = onUserPageRequest,
            onDailyRoutineRegisterPageRequest = onDailyRoutineRegisterPageRequest,
            onDailyRoutineClick = onDailyRoutineClick,
            onMemoryCardClick = onMemoryCardClick
        )
    }
}