package com.teampatch.memorystorage_detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.theme.HarmonyTheme

@Composable
fun MemoryStorageDetailScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {

        }
    )
}

@Preview
@Composable
private fun MemoryStorageDetailScreenPreview() {
    HarmonyTheme {
        MemoryStorageDetailScreen()
    }
}