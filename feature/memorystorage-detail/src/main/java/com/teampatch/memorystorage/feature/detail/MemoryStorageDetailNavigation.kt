package com.teampatch.memorystorage.feature.detail

import androidx.hilt.navigation.compose.hiltViewModel
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
    navigate(MemoryStorageDetailConversationRoute.routeWithArgs(memoryCardId), navOptions, navigatorExtras)
}

fun NavGraphBuilder.addMemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onRestartConversation: () -> Unit,
    onDismiss: () -> Unit,
) {
    composable(
        route = "memory_storage_detail/{memoryCardId}",
        arguments = listOf(navArgument("memoryCardId") { type = NavType.StringType })
    ) { backStackEntry ->
        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable

        // 여기에서 MemoryStorageDetailRoute는 데이터 클래스이므로 View처럼 호출 ❌
        MemoryStorageDetailScreen(
            memoryCardId = memoryCardId,
            memoryStorageDetailViewModel = hiltViewModel(),
            onBackRequest = onBackRequest,
            onRestartConversation = onRestartConversation,
            onDismiss = onDismiss
        )
    }
}

fun NavGraphBuilder.addMemoryStorageDetailConversationScreen() {
    composable(
        route = "memory_storage_detail_conversation/{memoryCardId}",
        arguments = listOf(navArgument("memoryCardId") { type = NavType.StringType })
    ) { backStackEntry ->
        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable

        ConversationView(
            memoryCardId = memoryCardId, // 필요하다면 전달
            onDismiss = { /* TODO: dismiss logic */ },
            onRestartConversation = { /* TODO: restart logic */ }
        )
    }
}