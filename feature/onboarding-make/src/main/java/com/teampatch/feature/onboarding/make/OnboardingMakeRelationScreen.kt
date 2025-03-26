package com.teampatch.feature.onboarding.make

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
internal fun OnboardingMakeRelationScreen(
    onBackRequest: () -> Unit,
    onInviteGrandParentsScreenRequest: () -> Unit,
) {
    var name by remember { mutableStateOf("") }

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_make_relation)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_make_relation)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_make_relation)[2])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_relation),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onInviteGrandParentsScreenRequest() },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("다음")
            }
        }
    ) {
        CustomTextField(
            name = name,
            onNameChange = { name = it }
        )
    }
}

@Composable
fun CustomTextField(
    name: String,
    onNameChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.text_onboarding_make_relation_title),
                    fontFamily = PretendardFontFamily,
                    color = BL,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 9.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { onNameChange(it) },
                    enabled = true,
                    placeholder = { Text("예) 손녀", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(G1, RoundedCornerShape(10.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray,
                        disabledContainerColor = Color(0xFFF5F5F5)
                    )
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.text_onboarding_make_name_title),
                    fontFamily = PretendardFontFamily,
                    color = BL,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 9.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { onNameChange(it) },
                    enabled = true,
                    placeholder = { Text("이름을 입력해 주세요.", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(G1, RoundedCornerShape(10.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray,
                        disabledContainerColor = Color(0xFFF5F5F5)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingMakeRelationScreenPreview() {
    HarmonyTheme {
        OnboardingMakeRelationScreen(
            onBackRequest = {},
            onInviteGrandParentsScreenRequest = {}
        )
    }
}