package com.teampatch.feature.member.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val MEMBER_HOME_ROUTE = "member_home"

fun NavController.navigateToMemberHomeScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route = MEMBER_HOME_ROUTE, navOptions = navOptions, navigatorExtras = navigatorExtras)
}


fun NavGraphBuilder.addMemberHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
) {
    composable(MEMBER_HOME_ROUTE) {
        MemberHomeRoute(
            onUserPageRequest = onUserPageRequest,
            onDailyRoutineClick = onDailyRoutineClick,
            onMemoryCardClick = onMemoryCardClick
        )
    }
}