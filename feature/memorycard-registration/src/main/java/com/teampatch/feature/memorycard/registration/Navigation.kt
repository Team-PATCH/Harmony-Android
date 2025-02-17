package com.teampatch.feature.memorycard.registration

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MemoryCardRegistrationRoute(
    val memoryCardId: String,
)

fun NavController.navigateToMemoryCardRegistrationScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(MemoryCardRegistrationRoute(memoryCardId), navOptions, navigatorExtras)
}

fun NavGraphBuilder.addMemoryCardRegistrationScreen(
    onDismissRequest: () -> Unit,
    onMemoryStorePageRequest: () -> Unit,
) {
    composable<MemoryCardRegistrationRoute> {
        MemoryCardRegistrationRoute(
            onDismissRequest = onDismissRequest,
            onMemoryStorePageRequest = onMemoryStorePageRequest
        )
    }
}