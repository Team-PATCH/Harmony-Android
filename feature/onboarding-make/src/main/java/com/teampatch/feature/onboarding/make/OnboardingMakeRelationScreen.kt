package com.teampatch.feature.onboarding.make

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.feature.onboarding.make.R.array.title_onboarding_make_relation
import com.teampatch.feature.onboarding.make.R.string.text_onboarding_make_grandson
import com.teampatch.feature.onboarding.make.R.string.text_onboarding_make_name_placeholder
import com.teampatch.feature.onboarding.make.R.string.text_onboarding_make_next
import com.teampatch.feature.onboarding.make.model.GroupMakingEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun OnboardingMakeRelationRoute(
    onBackRequest: () -> Unit,
    onProfileSettingsScreenRequest: () -> Unit,
    viewModel: OnboardingMakeRelationViewModel = hiltViewModel(),
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val context: Context = LocalContext.current

    OnboardingMakeRelationScreen(
        onBackRequest = onBackRequest,
        onProfileSettingsScreenRequest = { relation: String, name: String ->
            viewModel.createGroup(relation, name)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.isGroupMakingEvent
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    is GroupMakingEvent.Success -> onProfileSettingsScreenRequest()
                    is GroupMakingEvent.Error -> {
                        Toast.makeText(
                            context,
                            "그룹 생성 과정에서 에러가 발생하였습니다.\n다시 시도 해주세요.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is GroupMakingEvent.Init -> {}
                    is GroupMakingEvent.Loading -> {
                        Toast.makeText(context, "그룹 생성 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}

@Composable
internal fun OnboardingMakeRelationScreen(
    onBackRequest: () -> Unit,
    onProfileSettingsScreenRequest: (relation: String, name: String) -> Unit,
) {
    var relation by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }

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
                Log.e("TitleCheck", "Error: Missing Strings")
            }
        },
        subtext = stringResource(R.string.subtext_onboarding_make_relation),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onProfileSettingsScreenRequest(relation, name) },
                enabled = relation.isNotBlank() && name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(stringResource(text_onboarding_make_next))
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
                placeholder = {
                    Text(
                        stringResource(text_onboarding_make_grandson),
                        color = Color.Gray
                    )
                },
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
                placeholder = {
                    Text(
                        stringResource(text_onboarding_make_name_placeholder),
                        color = Color.Gray
                    )
                },
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
            onProfileSettingsScreenRequest = { _, _ -> }
        )
    }
}