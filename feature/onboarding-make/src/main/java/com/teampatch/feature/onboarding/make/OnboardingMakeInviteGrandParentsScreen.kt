package com.teampatch.feature.onboarding.make

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.R.drawable.btn_share_code_invitation
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.feature.onboarding.make.R.array.title_onboarding_make_invite_gp

@Composable
internal fun OnboardingMakeInviteGrandParentsScreen(
    onBackRequest: () -> Unit,
    onRelationScreenRequest: () -> Unit,
) {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(title_onboarding_make_invite_gp)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(title_onboarding_make_invite_gp)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(title_onboarding_make_invite_gp)[2])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_invite_gp),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onRelationScreenRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("다음")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* TODO: 클릭 이벤트 */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // 배경 투명
                ),
                contentPadding = PaddingValues(0.dp), // 내부 패딩 제거
                modifier = Modifier.fillMaxWidth() // 가로 전체 확장
            ) {
                Image(
                    painter = painterResource(btn_share_code_invitation), // 이미지 리소스
                    contentDescription = "Button Image",
                    modifier = Modifier
                        .fillMaxWidth() // 가로 전체 확장
                        .aspectRatio(2f), // 비율 유지 (적절한 값 조정 가능)
                    contentScale = ContentScale.Fit // 이미지가 잘리지 않도록 설정
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingMakeInviteGrandParentsScreenPreview() {
    HarmonyTheme {
        OnboardingMakeInviteGrandParentsScreen(
            onBackRequest = {},
            onRelationScreenRequest = {}
        )
    }
}