package com.teampatch.feature.profile.edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val PROFILE_EDIT_ROUTE = "profile_edit"

fun NavController.navigateToProfileEditScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(PROFILE_EDIT_ROUTE, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addProfileEditScreen(
    onCompleteRequest: () -> Unit
) {
    composable(PROFILE_EDIT_ROUTE) {
        ProfileEditRoute(onCompleteRequest = onCompleteRequest)
    }
}