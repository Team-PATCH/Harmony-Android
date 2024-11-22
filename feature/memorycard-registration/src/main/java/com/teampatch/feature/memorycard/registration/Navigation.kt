package com.teampatch.feature.memorycard.registration

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MemoryCardRegistrationRoute(
    val memoryCardId: String
)

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