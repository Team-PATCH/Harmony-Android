package com.teampatch.feature.onboarding.enter

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.feature.onboarding.enter.R.array.title_onboarding_enter_space
import com.teampatch.feature.onboarding.enter.R.drawable.img_onboarding_enter_space
import com.teampatch.feature.onboarding.enter.R.string.text_onboarding_enter_space
import com.teampatch.feature.onboarding.enter.viewmodel.OnboardingEnterSpaceViewModel

@Composable
internal fun OnboardingEnterSpaceRoute(
    onBackRequest: () -> Unit,
    onHomeRouteRequest: () -> Unit,
) {
    // HiltViewModel 주입은 여기서 하는 것이 일반적입니다.
    val viewModel: OnboardingEnterSpaceViewModel = hiltViewModel()

    OnboardingEnterSpaceScreen(
        profileImageUris = viewModel.profileImageUris.value, // ViewModel로부터 State를 가져와 전달
        onBackRequest = onBackRequest,
        onHomeRouteRequest = onHomeRouteRequest
    )
}

@Composable
fun OnboardingEnterSpaceScreen(
    profileImageUris: List<Uri>,
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
                Text(stringResource(text_onboarding_enter_space))
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            profileImageUris.forEach { uri ->
                Log.d("ProfileImageUri", "Uri: $uri") // uri 값 확인

                Image(
                    painter = rememberAsyncImagePainter(
                        model = uri,
                        placeholder = painterResource(id = ic_my_appbar),
                        error = painterResource(id = ic_my_appbar)
                    ),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(144.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
    Log.d("ProfileImageUris", "profileImageUris: $profileImageUris")
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEnterSpaceScreenPreview() {
    HarmonyTheme {
        OnboardingEnterSpaceScreen(
            profileImageUris = emptyList(),
            onBackRequest = {},
            onHomeRouteRequest = {}
        )
    }
}