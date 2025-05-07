package com.teampatch.memorystorage.feature.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

@Serializable
data class MemoryStorageDetailRoute(val memoryCardId: String) {
    companion object {
        fun routeWithArgs(memoryCardId: String) = "memory_storage_detail/$memoryCardId"
    }
}

@Serializable
data class MemoryStorageDetailConversationRoute(val memoryCardId: String) {
    companion object {
        fun routeWithArgs(memoryCardId: String) = "memory_storage_detail_conversation/$memoryCardId"
    }
}

fun NavController.navigateToMemoryStorageDetailScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(MemoryStorageDetailRoute.routeWithArgs(memoryCardId), navOptions, navigatorExtras)
}

fun NavController.navigateToConversationScreen(
    memoryCardId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(
        MemoryStorageDetailConversationRoute.routeWithArgs(memoryCardId),
        navOptions,
        navigatorExtras
    )
}

fun NavGraphBuilder.addMemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onRestartConversation: () -> Unit,
) {
    composable(
        route = "memory_storage_detail/{memoryCardId}",
        arguments = listOf(navArgument("memoryCardId") { type = NavType.StringType })
    ) {
        // ViewModel을 내부에서 사용하므로 별도로 주입할 필요 없음
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
    composable(
        route = "memory_storage_detail_conversation/{memoryCardId}",
        arguments = listOf(navArgument("memoryCardId") { type = NavType.StringType })
    ) {
        ConversationView(
            onDismiss = onDismiss,
            onRestartConversation = onRestartConversation
        )
    }
}