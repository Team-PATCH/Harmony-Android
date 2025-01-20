package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.feature.onboarding.R

@Composable
fun OnBoardingLayout(
    onBackRequest: () -> Unit,
    title: AnnotatedString,
    subtext: String,
    content: @Composable () -> Unit, // content 인자를 받음
) {
    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                title = {},
                actions = {}
            )
        }
    ) { scaffoldPaddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = title,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = subtext,
                        color = G5,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 34.dp)
                        .background(G1)
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * OnboardingLayout Screen과 다르게 ui를 재사용하기 위해 만든 것이라고 보면됨.
 * 여기에 둘 예정
 *
 * 여기서 OnboardingStartRoute를 만들고
 * 파라미터로 request 2개, route 2개, 스크린도 2개(MakeGroup, Enter) 만들기
 */

@Composable
fun OnboardingStartScreen(
    onboardingMakeGroupRequest: () -> Unit,
    onboardingEnterScreenRequest: () -> Unit,
) {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_make_space)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_make_space)[1])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_space),
        onBackRequest = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // 배경색 설정
        ) {
            Image(
                painter = painterResource(com.teampatch.core.designsystem.R.drawable.btn_make_space_onboarding),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onboardingMakeGroupRequest() }
            )

            // Spacer 대신 Box로 배경색을 설정한 여백 추가
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(Color.White) // 여백의 배경색을 설정
            )

            Image(
                painter = painterResource(com.teampatch.core.designsystem.R.drawable.btn_enter_space_onboarding),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onboardingEnterScreenRequest() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingStartScreenPreview() {
    HarmonyTheme {
        OnboardingStartScreen(
            onboardingMakeGroupRequest = { },
            onboardingEnterScreenRequest = { }
        )
    }
}