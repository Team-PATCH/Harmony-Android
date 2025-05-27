package com.teampatch.daily.certify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teampatch.core.designsystem.theme.HarmonyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCertifyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromNotification = intent.getBooleanExtra("FROM_NOTIFICATION", false)

        setContent {
            HarmonyTheme {
                val navController = rememberNavController()
                val viewModel: DailyCertifyViewModel = viewModel() // ✅ Activity 범위에서 생성

                NavHost(
                    navController = navController,
                    startDestination = "daily_alarm"
                ) {
                    composable("daily_alarm") {
                        DailyAlarmRoute(
                            fromNotification = isFromNotification,
                            onDismissRequest = { finish() },
                            onPickImageScreenRequest = {
                                navController.navigate(DailyCertifyScreenRoute)
                            }
                        )
                    }

                    // Type-safe composable 추가
                    composable<DailyCertifyScreenRoute> {
                        DailyCertifyRoute(
                            viewModel = viewModel,
                            onBackRequest = { navController.popBackStack() },
                            onCertifyCompleteRequest = { viewModel.completeCertify() },
                            onNavigateToDetailRequest = {
                                navController.navigate(DailyCertifyDetailScreenRoute)
                            }
                        )
                    }

                    composable<DailyCertifyDetailScreenRoute> {
                        DailyCertifyDetailRoute(
                            viewModel = viewModel,
                            onBackRequest = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}