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
import com.teampatch.feature.onboarding.enter.R.drawable.img_onboarding_enter_space
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
                Text("가족 공간 입장하기")
            }
        },
        image = {
            Image(
                painter = painterResource(img_onboarding_enter_space),
                contentDescription = "Onboarding Illustration",
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        imagePadding = 15.dp // ✅ bottomBar가 있을 때 이미지와의 간격 조정
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