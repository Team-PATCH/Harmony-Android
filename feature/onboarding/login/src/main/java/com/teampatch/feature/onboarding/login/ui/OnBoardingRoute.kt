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
fun OnBoardingRoute(
    title: String,
    content: @Composable () -> Unit,  // content 인자를 받음
    onBackClick: () -> Unit,
    onNextClick: (() -> Unit)? = null,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {
    // 현재 Context 가져오기
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title 표시
        Text(text = title, style = MaterialTheme.typography.headlineSmall)

        // content 인자를 통해 전달된 컴포저블 UI 구성 요소 표시
        content()


        // 뒤로 가기 버튼
        Button(onClick = onBackClick) {
            Text(text = "Back")
        }

        // 다음 단계로 이동 버튼
        if (onNextClick != null) {
            Button(onClick = onNextClick) {
                Text(text = "Next")
            }
        }
    }
}
