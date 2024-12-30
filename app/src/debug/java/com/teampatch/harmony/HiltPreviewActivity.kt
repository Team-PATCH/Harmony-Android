package com.teampatch.harmony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.teampatch.core.designsystem.theme.HarmonyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltPreviewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HarmonyTheme {
                // Screen
            }
        }
    }
}