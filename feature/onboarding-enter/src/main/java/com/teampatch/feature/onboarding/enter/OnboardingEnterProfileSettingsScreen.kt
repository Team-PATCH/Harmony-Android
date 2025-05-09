package com.teampatch.feature.onboarding.enter

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R.drawable.ic_camera_profile
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.OnBoardingLayout
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.feature.onboarding.enter.R.array.title_onboarding_enter_profile
import com.teampatch.feature.onboarding.enter.R.string.subtext_onboarding_enter_name
import com.teampatch.feature.onboarding.enter.R.string.text_onboarding_enter_enter_space
import com.teampatch.feature.onboarding.enter.viewmodel.OnboardingEnterInvitationCodeViewModel

@Composable
internal fun OnboardingEnterProfileSettingsRoute(
    viewModel: OnboardingEnterInvitationCodeViewModel = hiltViewModel(),
    onBackRequest: () -> Unit,
    onEnterSpaceScreenRequest: (Uri) -> Unit, // Uri를 전달받을 수 있도록 수정
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    OnboardingEnterProfileSettingsScreen(
        profileImageUri = viewModel.profileImageUri.value,
        onBackRequest = onBackRequest,
        onProfileImageUpdate = { uri -> viewModel.updateProfileImage(uri) },
        onEnterSpaceScreenRequest = { uri ->
            onEnterSpaceScreenRequest(uri)
        }
    )
}

@Composable
internal fun OnboardingEnterProfileSettingsScreen(
    profileImageUri: Uri?,
    onBackRequest: () -> Unit,
    onProfileImageUpdate: (Uri) -> Unit,
    onEnterSpaceScreenRequest: (Uri) -> Unit,
) {
    var selectedImageUri by remember { mutableStateOf(profileImageUri ?: Uri.EMPTY) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                onProfileImageUpdate(uri) // ViewModel 업데이트
            }
        }
    )

    val titles = stringArrayResource(title_onboarding_enter_profile)

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
        subtext = stringResource(subtext_onboarding_enter_name),
        onBackRequest = { onBackRequest() },
        bottomBar = {
            DefaultButton(
                onClick = { onEnterSpaceScreenRequest(selectedImageUri) }, // 이미지 URI를 onEnterSpaceScreenRequest에 전달
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(stringResource(text_onboarding_enter_enter_space))
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .noRippleClickable {
                    val pickerRequest =
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    photoPicker.launch(pickerRequest)
                }
        ) {
            // 이미지를 감싸는 Box 추가 (아이콘을 정렬하기 위해)
            Box(
                modifier = Modifier.size(144.dp) // 이미지 크기와 동일한 크기
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = profileImageUri ?: ic_my_appbar,
                        placeholder = painterResource(ic_my_appbar),
                        error = painterResource(ic_my_appbar)
                    ),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(144.dp)
                        .clip(CircleShape)
                )

                // 카메라 아이콘을 이미지의 오른쪽 아래에 정렬
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .background(MainGreen, CircleShape)
                        .align(Alignment.BottomEnd) // ✅ 이미지 기준으로 오른쪽 아래 정렬
                ) {
                    Icon(
                        painter = painterResource(ic_camera_profile),
                        contentDescription = "camera",
                        tint = WH
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingMakeProfileSettingsScreenPreview() {
    HarmonyTheme {
        OnboardingEnterProfileSettingsScreen(
            profileImageUri = null,
            onBackRequest = {},
            onProfileImageUpdate = {},
            onEnterSpaceScreenRequest = {}
        )
    }
}