package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.teampatch.core.designsystem.R.drawable.text_intro_harmony),
                contentDescription = "Text Introduce Harmony",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
            )

            Spacer(modifier = modifier.height(29.3.dp))

            Image(
                painter = painterResource(id = com.teampatch.core.designsystem.R.drawable.logo_harmony),
                contentDescription = "Logo Harmony",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
            )

            Spacer(modifier = modifier.height(644.dp))

            Button(
                onClick = { /* Handle Kakao Login */ },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE812)), // 배경색은 이미지를 꽉 채우면 안 보이게 됩니다.
                shape = RoundedCornerShape(10.dp)
            ) {
                // 이미지 리소스를 painterResource로 불러오고 버튼을 꽉 채움
                Image(
                    painter = painterResource(id = com.teampatch.core.designsystem.R.drawable.kakao_login_medium_wide), // 카카오 로그인 이미지
                    contentDescription = "Kakao Login",
                    modifier = Modifier.fillMaxSize(), // 버튼 크기를 꽉 채움
                    contentScale = ContentScale.Crop // 이미지가 버튼에 맞게 잘리거나 확장됨
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}