package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.component.TypeWriterText
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.feature.onboarding.R


@Composable
fun OnboardingEnterScreen() {
    // 상태 변수로 초대 코드의 각 자리를 저장
    var code by remember { mutableStateOf("") }

    // 버튼 활성화 여부 (5자리 모두 입력되면 활성화)
    val isNextEnabled = code.length == 5

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_enter_code)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_enter_code)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_enter_code)[2])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_enter_code),
        onBackRequest = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // 초대 코드 입력 필드
            OutlinedTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 5) {
                        code = it
                    }
                },
                label = { Text("초대코드") },
                placeholder = { Text("12345") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation() // 코드 숨김 처리
            )

            Spacer(modifier = Modifier.height(402.dp))

            // 다음 버튼
            Button(
                onClick = { /* 초대 코드 확인 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // 버튼의 높이 설정
                enabled = isNextEnabled, // 활성화 여부: 5자리가 아니면 비활성화
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isNextEnabled) Color(0xFF4CAF50) else Color(0xFFD3D3D3), // 활성화시 초록색, 비활성화시 회색
                    contentColor = Color.White
                )
            ) {
                Text(text = "다음")
            }
        }
    }
}

@Composable
fun InputProfileSettings() {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_setting_prfile_image)[0])
            }
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_setting_prfile_image)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_setting_prfile_image)[2])
            }
        },
        subtext = "",
        onBackRequest = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally // 요소를 수평 중앙으로 정렬

        ) {
            Image(
                painter = painterResource(com.teampatch.core.designsystem.R.drawable.btn_add_profile),
                null,
                contentScale = ContentScale.Crop, // 이미지가 잘리지 않고 버튼 안에 맞춰짐
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(120.dp)) // 이거 수정해야됨


            SpeechBubble {
                Text(text = "조다은님에 대해서 \n더 깊게 알아가볼까요?",
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Image(painter = painterResource(com.teampatch.core.designsystem.R.drawable.btn_enter_space), null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEnterScreenPreview() {
    HarmonyTheme {
        OnboardingEnterScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun InputProfileSettingsPreview() {
    HarmonyTheme {
        InputProfileSettings()
    }
}