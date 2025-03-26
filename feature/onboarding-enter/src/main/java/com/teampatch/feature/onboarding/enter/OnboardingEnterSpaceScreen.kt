package com.teampatch.feature.onboarding.enter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.feature.onboarding.enter.R.array.title_onboarding_enter_space
import com.teampatch.feature.onboarding.enter.R.string.text_onboarding_enter_speech_bubble

@Composable
fun OnboardingEnterSpaceScreen(
    onBackRequest: () -> Unit,
    onHomeRouteRequest: () -> Unit,
) {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(title_onboarding_enter_space)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(title_onboarding_enter_space)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(title_onboarding_enter_space)[2])
            }
        },
        subtext = "",
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onHomeRouteRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("공간 입장하기")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // 전체 영역 차지
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally // 요소를 수평 중앙으로 정렬
        ) {
            Image(
                painter = painterResource(ic_my_appbar),
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(144.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(148.dp)) // TODO: 이거 어떻게 위치 조정하지...

            SpeechBubble(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp) // bottomBar와 동일한 패딩
            ) {
                Text(
                    text = stringResource(text_onboarding_enter_speech_bubble),
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEnterSpaceScreenPreview() {
    HarmonyTheme {
        OnboardingEnterSpaceScreen(
            onBackRequest = {},
            onHomeRouteRequest = {}
        )
    }
}