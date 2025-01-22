package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R.drawable.btn_add_profile
import com.teampatch.core.designsystem.R.drawable.btn_enter_space
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.feature.onboarding.R

@Composable
fun OnboardingEnterInvitationCodeScreen(
    onNextClick: () -> Unit,
) {
    // 상태 변수로 초대 코드의 각 자리를 저장
    var code by remember { mutableStateOf(listOf("", "", "", "", "")) }
    val isNextEnabled = code.all { it.isNotEmpty() } // 5자리가 모두 입력되면 활성화

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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val focusManager = LocalFocusManager.current
                code.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
                            .clickable { /* Focus the respective field */ },
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = code[index],
                            onValueChange = { value ->
                                if (value.length <= 1) {
                                    code = code.toMutableList().apply {
                                        this[index] = value
                                    }
                                    // 다음 입력칸으로 포커스 이동
                                    if (value.isNotEmpty() && index < code.size - 1) {
                                        focusManager.moveFocus(FocusDirection.Next)
                                    }
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(402.dp))

            // 다음 버튼
            Button(
                onClick = {
                    /* 초대 코드 확인 처리 */
                    onNextClick()
                },
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
fun OnboardingEnterSpaceScreen(
    onNextClick: () -> Unit,
) {
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
                painter = painterResource(btn_add_profile),
                null,
                contentScale = ContentScale.Crop, // 이미지가 잘리지 않고 버튼 안에 맞춰짐
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(120.dp)) // 이거 수정해야됨

            SpeechBubble {
                Text(
                    text = "조다은님에 대해서 \n더 깊게 알아가볼까요?",
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Image(
                painter = painterResource(btn_enter_space),
                null,
                modifier = Modifier.fillMaxWidth()
                    .clickable { onNextClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEnterInvitationCodeScreenPreview() {
    HarmonyTheme {
        OnboardingEnterInvitationCodeScreen(
            onNextClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEnterSpaceScreenPreview() {
    HarmonyTheme {
        OnboardingEnterSpaceScreen(
            onNextClick = {}
        )
    }
}