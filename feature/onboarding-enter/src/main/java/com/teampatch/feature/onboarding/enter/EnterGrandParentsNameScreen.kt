package com.teampatch.feature.onboarding.enter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen

@Composable
internal fun OnboardingEnterRoute(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: () -> Unit,
    viewModel: OnboardingEnterViewModel = hiltViewModel(),
) {
}

@Composable
internal fun EnterGrandParentsNameScreen(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: (String) -> Unit,
) {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_enter_name)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_enter_name)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_enter_name)[2])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_enter_name),
        onBackRequest = { onBackRequest() }
    ) {
        CustomDropdownAndTextField()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownAndTextField() {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("할머니") } // 기본 선택값
    val options = listOf("할머니", "할아버지", "어머니", "아버지")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .weight(1f)
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedText = option
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = "성함",
            onValueChange = {},
            enabled = false,
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                disabledContainerColor = Color(0xFFF5F5F5) // 배경색 설정
            )
        )
    }
}

@Preview
@Composable
private fun EnterGrandParentsNameScreenPreview() {
    HarmonyTheme {
        EnterGrandParentsNameScreen(
            onBackRequest = {},
            onEnterRelationScreenRequest = {}
        )
    }
}