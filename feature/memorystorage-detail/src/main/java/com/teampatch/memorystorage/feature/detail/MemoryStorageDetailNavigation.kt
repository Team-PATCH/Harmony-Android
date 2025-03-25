package com.teampatch.memorystorage.feature.detail

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MemoryStorageDetailRoute(val memoryCardId: String)

@SuppressLint("WrongNavigateRouteType")
fun NavController.navigateToMemoryStorageDetailScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(MemoryStorageDetailRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addMemoryStorageDetailScreen() {
    composable<MemoryStorageDetailRoute> {
        MemoryStorageDetailRoute()
    }
}