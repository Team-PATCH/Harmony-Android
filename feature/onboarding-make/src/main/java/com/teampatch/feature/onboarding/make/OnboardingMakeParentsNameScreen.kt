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
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.feature.onboarding.make.R.array.title_onboarding_make_option_of_gp
import com.teampatch.feature.onboarding.make.R.string.text_onboarding_make_name
import com.teampatch.feature.onboarding.make.R.string.text_onboarding_make_next

@Composable
internal fun OnboardingMakeParentsNameScreen(
    onBackRequest: () -> Unit,
    onShareInvitationScreenRequest: () -> Unit,
) {
    var selectedText by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append(stringArrayResource(R.array.title_onboarding_make_name)[0])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_make_name)[1])
            }
            withStyle(style = SpanStyle(color = BL)) {
                append(stringArrayResource(R.array.title_onboarding_make_name)[2])
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_name),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onShareInvitationScreenRequest() },
                enabled = selectedText.isNotBlank() && name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(stringResource(text_onboarding_make_next))
            }
        }
    ) {
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
    val options = stringArrayResource(title_onboarding_make_option_of_gp)

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
                placeholder = { Text(stringArrayResource(title_onboarding_make_option_of_gp)[0], color = Color.Gray) },
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
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectedTextChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            enabled = true,
            placeholder = { Text(stringResource(text_onboarding_make_name), color = Color.Gray) },
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
private fun OnboardingMakeParentsNameScreenPreview() {
    HarmonyTheme {
        OnboardingMakeParentsNameScreen(
            onBackRequest = {},
            onShareInvitationScreenRequest = {}
        )
    }
}