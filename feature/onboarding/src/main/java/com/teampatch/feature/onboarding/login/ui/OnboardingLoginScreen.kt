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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.R

@Composable
fun OnboardingLoginScreen(
    onKakaoLoginRequest: () -> Unit,
    onPermissionNotificationRequest: () -> Unit,
) {
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
                    onClick = { /* Handle Kakao Login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(68.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE812)), // 배경색은 이미지를 꽉 채우면 안 보이게 됩니다.
                    shape = RoundedCornerShape(10.dp)
                ) {
                    // 이미지 리소스를 painterResource로 불러오고 버튼을 꽉 채움
                    Image(
                        painter = painterResource(id = R.drawable.kakao_login_medium_wide), // 카카오 로그인 이미지
                        contentDescription = "Kakao Login",
                        modifier = Modifier.fillMaxSize(), // 버튼 크기를 꽉 채움
//                    contentScale = ContentScale.Crop // 이미지가 버튼에 맞게 잘리거나 확장됨
//                    modifier = Modifier.fillMaxHeight(), // 버튼 높이에 맞게 이미지 채우기
                        contentScale = ContentScale.Fit // 이미지가 잘리지 않고 버튼 안에 맞춰짐
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLoginScreenPreview() {
    OnboardingLoginScreen(
        onKakaoLoginRequest = {},
        onPermissionNotificationRequest = {}
    )
}