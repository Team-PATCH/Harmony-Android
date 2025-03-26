package com.teampatch.feature.onboarding.make

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen

@Composable
internal fun OnboardingMakeGroupRoute(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: () -> Unit,
    viewModel: OnboardingMakeViewModel = hiltViewModel(),
) {
}

@Composable
internal fun OnboardingMakeParentsNameScreen(
    onBackRequest: () -> Unit,
    onEnterRelationScreenRequest: () -> Unit,
) {
    var selectedText by remember { mutableStateOf("") } // ✅ 상태를 상위에서 관리
    var name by remember { mutableStateOf("") } // ✅ 상태를 상위에서 관리

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
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onEnterRelationScreenRequest() },
                enabled = selectedText.isNotBlank() && name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("추가하기")
            }
        }
    ) {
        // ✅ 상태를 전달
        CustomDropdownAndTextField(
            selectedText = selectedText,
            onSelectedTextChange = { selectedText = it },
            name = name,
            onNameChange = { name = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownAndTextField(
    selectedText: String,
    onSelectedTextChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("할머니", "할아버지")

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
                placeholder = { Text("할머니", color = Color.Gray) },
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = MainGreen
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // ✅ ExposedDropdownMenuBoxScope 내에서 사용해야 함
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectedTextChange(option) // ✅ 부모에 값 전달
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) }, // ✅ 부모에 값 전달
            enabled = true, // ✅ 입력 가능하도록 설정
            placeholder = { Text("성함", color = Color.Gray) }, // ✅ 입력 전 힌트 표시
            modifier = Modifier
                .weight(1f)
                .background(G1, RoundedCornerShape(10.dp)),
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
        OnboardingMakeParentsNameScreen(
            onBackRequest = {},
            onEnterRelationScreenRequest = {}
        )
    }
}