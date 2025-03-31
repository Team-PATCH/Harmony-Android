package com.teampatch.feature.onboarding.make

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.feature.onboarding.make.R.array.title_onboarding_make_relation

@Composable
internal fun OnboardingMakeRelationScreen(
    onBackRequest: () -> Unit,
    onProfileSettingsScreenRequest: () -> Unit,
) {
    var relation by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val titles = stringArrayResource(title_onboarding_make_relation)

    OnBoardingLayout(
        title = buildAnnotatedString {
            if (titles.size >= 3) {
                withStyle(style = SpanStyle(color = BL)) {
                    append(titles[0])
                }
                withStyle(style = SpanStyle(color = MainGreen)) {
                    append(titles[1])
                }
                withStyle(style = SpanStyle(color = BL)) {
                    append(titles[2])
                }
            } else {
                append("Error: Missing Strings")
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_relation),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onProfileSettingsScreenRequest() },
                enabled = relation.isNotBlank() && name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("다음")
            }
        }
    ) {
        CustomTextField(
            relation = relation,
            onRelationChange = { relation = it },
            name = name,
            onNameChange = { name = it }
        )
    }
}

@Composable
fun CustomTextField(
    relation: String,
    onRelationChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
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
                value = relation,
                onValueChange = { onRelationChange(it) },
                enabled = true,
                placeholder = { Text("예) 손녀", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(G1),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = G2
                )
            )
        }

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
                    .background(G1),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = G2

                )
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingMakeRelationScreenPreview() {
    HarmonyTheme {
        OnboardingMakeRelationScreen(
            onBackRequest = {},
            onProfileSettingsScreenRequest = {}
        )
    }
}