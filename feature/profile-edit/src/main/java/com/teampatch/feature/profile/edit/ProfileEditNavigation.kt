package com.teampatch.feature.profile.edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ProfileEditRoute

fun NavController.navigateToProfileEditScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(ProfileEditRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addProfileEditScreen(
    onCompleteRequest: () -> Unit,
) {
    composable<ProfileEditRoute> {
        ProfileEditRoute(
            onCompleteRequest = onCompleteRequest
        )
    }
}