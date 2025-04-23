package com.teampatch.memorystorage.feature.detail

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
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

//@SuppressLint("WrongNavigateRouteType")
//fun NavController.navigateToMemoryStorageDetailScreen(
//    memoryCardId: String,
//    navOptions: NavOptions? = null,
//    navigatorExtras: Navigator.Extras? = null,
//) {
//    navigate(MemoryStorageDetailRoute(memoryCardId), navOptions, navigatorExtras)
//}

//@SuppressLint("WrongNavigateRouteType")
//fun NavController.navigateToMemoryStorageDetailScreen(
//    memoryCardId: String,
//    navOptions: NavOptions? = null,
//    navigatorExtras: Navigator.Extras? = null,
//) {
//    navigate(
//        MemoryStorageDetailRoute(memoryCardId),
//        navOptions,
//        navigatorExtras
//    )
//}
//
//fun NavController.navigateToConversationScreen(
//    memoryCardId: String,
//    navOptions: NavOptions? = null,
//    navigatorExtras: Navigator.Extras? = null,
//) {
//    navigate(MemoryStorageDetailConversationRoute(memoryCardId), navOptions, navigatorExtras)
//}

// NavigationExtensions.kt
@SuppressLint("WrongNavigateRouteType")
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
    navigate(MemoryStorageDetailConversationRoute(memoryCardId), navOptions, navigatorExtras)
}

//fun NavGraphBuilder.addMemoryStorageDetailScreen(
//    onClickConversation: (String) -> Unit
//) {
//    composable<MemoryStorageDetailRoute> { backStackEntry ->
//        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable
//        MemoryStorageDetailScreen(
//            memoryCardId = memoryCardId,
//            onClickConversation = onClickConversation
//        )
//    }
//}
//fun NavGraphBuilder.addMemoryStorageDetailScreen(
//    onBackRequest: () -> Unit,
//    onRestartConversation: () -> Unit,
//    onDismiss: () -> Unit
//) {
//    composable<MemoryStorageDetailRoute> { backStackEntry ->
//        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable
//
//        // ViewModel이 Hilt로 DI되고 있으니 그대로 사용
//        MemoryStorageDetailRoute(
//            onBackRequest = onBackRequest,
//            onRestartConversation = onRestartConversation,
//            onDismiss = onDismiss
//        )
//    }
//}
//
//fun NavGraphBuilder.addMemoryStorageDetailConversationScreen() {
//    composable<MemoryStorageDetailConversationRoute> { backStackEntry ->
//        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable
//        MemoryStorageDetailConversationScreen(memoryCardId = memoryCardId)
//    }
//}

// NavGraphBuilderExtensions.kt
fun NavGraphBuilder.addMemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onRestartConversation: () -> Unit,
    onDismiss: () -> Unit
) {
    composable<MemoryStorageDetailRoute> { backStackEntry ->
        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable

        MemoryStorageDetailRoute(
            memoryStorageDetailViewModel = hiltViewModel(),
            onBackRequest = onBackRequest,
            onRestartConversation = onRestartConversation,
            onDismiss = onDismiss
        )
    }
}

fun NavGraphBuilder.addMemoryStorageDetailConversationScreen() {
    composable<MemoryStorageDetailConversationRoute> { backStackEntry ->
        val memoryCardId = backStackEntry.arguments?.getString("memoryCardId") ?: return@composable

        ConversationView(memoryCardId = memoryCardId)
    }
}