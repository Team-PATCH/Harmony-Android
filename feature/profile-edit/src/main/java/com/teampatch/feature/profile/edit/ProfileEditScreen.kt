package com.teampatch.feature.profile.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R.drawable.ic_camera_profile
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.DefaultTextField
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.designsystem.utils.previewPlaceholder
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.Role
import com.teampatch.feature.profile.edit.model.ProfileEditSideEffect
import com.teampatch.feature.profile.edit.model.ProfileEditUiState

@Composable
internal fun ProfileEditRoute(
    onCompleteRequest: () -> Unit,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val profileEditUiState by profileEditViewModel.profileEditUiState.collectAsStateWithLifecycle()

    if (!profileEditUiState.isLoading) {
        ProfileEditScreen(
            onBackRequest = onCompleteRequest,
            onEditClick = { profileEditViewModel.editProfile() },
            onRelationChange = profileEditViewModel::updateRelation,
            onNameChange = profileEditViewModel::updateName,
            onProfileImageChange = profileEditViewModel::updateProfileImage,
            profileEditUiState = profileEditUiState
        )
    }

    LaunchedEffect(Unit) {
        profileEditViewModel.sideEffect.collect {
            when (it) {
                is ProfileEditSideEffect.LoadError -> {
                    Toast.makeText(context, "유저 정보를 불러오는 도중 에러가 발생하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                is ProfileEditSideEffect.ProfileEditError -> {
                    Toast.makeText(context, "프로필 수정 중에 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }

                is ProfileEditSideEffect.ProfileEditSuccess -> {
                    onCompleteRequest()
                }
            }
        }
    }
}

@Composable
internal fun ProfileEditScreen(
    onBackRequest: () -> Unit,
    onEditClick: () -> Unit,
    onRelationChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    profileEditUiState: ProfileEditUiState,
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onProfileImageChange(uri)
                return@rememberLauncherForActivityResult
            }
        }
    )

    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                title = {
                    Text("프로필 수정")
                }
            )
        },
        bottomBar = {
            val buttonEnable: Boolean by remember(profileEditUiState) {
                derivedStateOf {
                    when (profileEditUiState.role) {
                        Role.VIP -> {
                            profileEditUiState.name.isNotBlank()
                        }

                        Role.MEMBER -> {
                            with(profileEditUiState) {
                                relation.isNotBlank() && name.isNotBlank()
                            }
                        }
                    }
                }
            }

            DefaultButton(
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                enabled = buttonEnable
            ) {
                Text(text = stringResource(R.string.btn_edit_bottom))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 44.dp)
                    .align(Alignment.CenterHorizontally)
                    .noRippleClickable {
                        val pickerRequest =
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        photoPicker.launch(pickerRequest)
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = when (val profileImage = profileEditUiState.profileImage) {
                            is Image.Uri -> profileImage.uri
                            is Image.Url -> profileImage.url
                            null -> ic_my_appbar
                        },
                        placeholder = previewPlaceholder(ic_my_appbar)
                    ),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(144.dp)
                        .clip(CircleShape)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(MainGreen, CircleShape)
                        .size(60.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(ic_camera_profile),
                        contentDescription = "camera",
                        tint = WH
                    )
                }
            }
            if (profileEditUiState.role == Role.MEMBER) {
                Text(
                    text = stringResource(R.string.text_relation_title),
                    color = BL,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 28.dp, top = 32.dp)
                )
                DefaultTextField(
                    value = profileEditUiState.relation,
                    onValueChange = onRelationChange,
                    textStyle = TextStyle(
                        color = BL,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    hint = { Text(text = stringResource(R.string.tf_relation_hint)) },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                )
            }
            Text(
                text = stringResource(R.string.text_name_title),
                color = BL,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 28.dp, top = 32.dp)
            )
            DefaultTextField(
                value = profileEditUiState.name,
                onValueChange = onNameChange,
                textStyle = TextStyle(
                    color = BL,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                hint = { Text(text = stringResource(R.string.tf_name_hint)) },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileEditScreenMemberPreview() {
    HarmonyTheme {
        ProfileEditScreen(
            onBackRequest = {},
            onEditClick = { },
            onRelationChange = { },
            onNameChange = { },
            onProfileImageChange = { },
            profileEditUiState = ProfileEditUiState(
                relation = "",
                name = "",
                profileImage = Image.Url("https://picsum.photos/200/300")
            )
        )
    }
}

@Preview
@Composable
private fun ProfileEditScreenVipPreview() {
    HarmonyTheme {
        ProfileEditScreen(
            onBackRequest = {},
            onEditClick = { },
            onRelationChange = { },
            onNameChange = { },
            onProfileImageChange = { },
            profileEditUiState = ProfileEditUiState(
                relation = "",
                name = "",
                profileImage = Image.Url("https://picsum.photos/200/300"),
                role = Role.VIP
            )
        )
    }
}