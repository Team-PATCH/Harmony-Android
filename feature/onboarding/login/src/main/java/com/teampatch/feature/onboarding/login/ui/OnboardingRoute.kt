package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnboardingRoute(
    onBackRequest: () -> Unit,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    // 현재 Context 가져오기
    val context = LocalContext.current
}
