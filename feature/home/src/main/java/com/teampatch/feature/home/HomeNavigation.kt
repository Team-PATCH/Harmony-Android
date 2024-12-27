package com.teampatch.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHomeScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(HomeRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineRegisterPageRequest: () -> Unit,
    onDailyRoutineClick: (dailyRoutineId: String) -> Unit,
    onMemoryCardClick: (memoryCardId: String) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            onUserPageRequest = onUserPageRequest,
            onDailyRoutineRegisterPageRequest = onDailyRoutineRegisterPageRequest,
            onDailyRoutineClick = onDailyRoutineClick,
            onMemoryCardClick = onMemoryCardClick
        )
    }
}
