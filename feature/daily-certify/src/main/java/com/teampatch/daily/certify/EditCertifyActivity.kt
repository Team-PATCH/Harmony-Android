package com.teampatch.daily.certify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.teampatch.core.designsystem.theme.HarmonyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCertifyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromNotification = intent.getBooleanExtra("FROM_NOTIFICATION", false)

        setContent {
            HarmonyTheme {
                DailyAlarmRoute(
                    fromNotification = isFromNotification,
                    onDismissRequest = {},
                    onPickImageScreenRequest = {}
                )
            }
        }
    }
}