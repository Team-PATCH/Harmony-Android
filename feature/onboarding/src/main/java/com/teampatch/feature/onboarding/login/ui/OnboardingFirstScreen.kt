package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.R

@Composable
internal fun OnboardingFirstScreen(
    onKakaoLoginRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
    onStartScreenRequest: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()
    val isPermissionGranted by viewModel.isPermissionGranted.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    LaunchedEffect(isLoginSuccessful, isPermissionGranted) {
//        if (isLoginSuccessful && !isPermissionGranted) {
//            onPermissionNotificationRequest()
//        } // 지금 뷰모델에선 false로 초기화하고 있고 추측하기로는 !가 반대를 의미하니까 true라고 생각했는데
        // 그게 아니라 false라면 반대로 밑에서도 !를 넣어서 false라고 한다면?
        if (isLoginSuccessful && !isPermissionGranted) {
            onStartScreenRequest()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Scaffold의 기본 패딩
                .padding(top = 135.dp, bottom = 8.dp) // Column 시작 위치에 추가 패딩
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                verticalArrangement = Arrangement.Top,
                verticalArrangement = Arrangement.SpaceBetween, // 첫 요소는 위, 마지막 요소는 아래에 붙음
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_logo_in_login),
                    contentDescription = "Logo Harmony",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(465.dp))

                Button(
                    onClick = { viewModel.loginKakao() },
//                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(68.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE812)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kakao_login_medium_wide),
                        contentDescription = "Kakao Login",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

//                // 에러 메시지 표시
//                errorMessage?.let { message ->
//                    Text(
//                        text = message,
//                        color = Color.Red,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLoginScreenPreview() {
    OnboardingFirstScreen(
        onKakaoLoginRequest = {},
        onPermissionNotificationRequest = {},
        onStartScreenRequest = {}
    )
}