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
data class MemoryStorageDetailConversationRoute(val memoryCardId: String)

fun NavController.navigateToMemoryStorageDetailScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(MemoryStorageDetailRoute(memoryCardId), navOptions, navigatorExtras)
}

fun NavController.navigateToConversationScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(
        MemoryStorageDetailConversationRoute(memoryCardId),
        navOptions,
        navigatorExtras
    )
}

fun NavGraphBuilder.addMemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onRestartConversation: () -> Unit,
) {
    composable<MemoryStorageDetailConversationRoute> {
        MemoryStorageDetailRoute(
            onBackRequest = onBackRequest,
            onRestartConversation = onRestartConversation
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