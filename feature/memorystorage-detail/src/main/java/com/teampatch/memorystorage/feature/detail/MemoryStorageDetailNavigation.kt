package com.teampatch.memorystorage.feature.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MemoryStorageDetailRoute(val memoryCardId: String)

@Serializable
data object MemoryStorageDetailConversationRoute

fun NavController.navigateToMemoryStorageDetailScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(MemoryStorageDetailRoute(memoryCardId), navOptions, navigatorExtras)
}

fun NavController.navigateToConversationScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(
        MemoryStorageDetailConversationRoute,
        navOptions,
        navigatorExtras
    )
}

fun NavGraphBuilder.addMemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onConversationViewRequest: () -> Unit,
    onRestartConversation: () -> Unit,
    deleteCardRequest: () -> Unit,
) {
    composable<MemoryStorageDetailRoute> {
        MemoryStorageDetailRoute(
            onBackRequest = onBackRequest,
            onConversationViewRequest = onConversationViewRequest,
            onRestartConversation = onRestartConversation,
            deleteCardRequest = deleteCardRequest
        )
    }
}

fun NavGraphBuilder.addMemoryStorageDetailConversationScreen(
    onDismiss: () -> Unit,
    onRestartConversation: () -> Unit,
) {
    composable<MemoryStorageDetailConversationRoute> {
        ConversationView(
            onDismiss = onDismiss,
            onRestartConversation = onRestartConversation
        )
    }
}