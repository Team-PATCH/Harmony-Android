package com.teampatch.feature.onboarding.login.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.R

private val requiredPermissions: Array<String> = arrayOf(
    android.Manifest.permission.POST_NOTIFICATIONS
)

@Composable
fun OnboardingPermissionNotificationScreen(
    onNextPageRequest: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    (context as? Activity)
                        ?.requestPermissions(requiredPermissions, 1)
                    onNextPageRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .height(68.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 투명한 배경
                contentPadding = PaddingValues(0.dp), // 버튼의 기본 내부 패딩 제거
                shape = RoundedCornerShape(10.dp)
            ) {
                // 이미지 리소스를 painterResource로 불러오고 버튼을 꽉 채움
                Image(
                    painter = painterResource(id = R.drawable.btn_start_harmony), // 카카오 로그인 이미지
                    contentDescription = "Harmoy Start Process",
                    modifier = Modifier.fillMaxSize(), // 이미지가 버튼의 크기를 꽉 채움
                    contentScale = ContentScale.Crop // 이미지가 버튼 크기에 맞춰 잘림
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 92.dp, bottom = 8.dp) // Column 시작 위치에 추가 패딩
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween, // 첫 요소는 위, 마지막 요소는 아래에 붙음
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_permission_to_notify),
                    contentDescription = "Permission Notification Image",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Image(
                    painter = painterResource(id = R.drawable.img_character_fullbody_mony),
                    contentDescription = "Full-Body Mony Character",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPermissionNotificationScreenPreview() {
    OnboardingPermissionNotificationScreen({})
}