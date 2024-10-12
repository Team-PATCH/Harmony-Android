package com.teampatch.feature.onboarding.login.ui

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
            Text(
                text = "가족과 함께 만드는 소중한 추억",
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "harmony.",
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.W700,
                fontSize = 40.sp,
                color = Color.Black,
                modifier = modifier.padding(bottom = 32.dp)
            )

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = { /* Handle Apple Login */ },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Apple로 계속하기",
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = modifier.height(12.dp))

            Button(
                onClick = { /* Handle Kakao Login */ },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE812)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "카카오로 계속하기",
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 18.sp,
                    color = Color.Black
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