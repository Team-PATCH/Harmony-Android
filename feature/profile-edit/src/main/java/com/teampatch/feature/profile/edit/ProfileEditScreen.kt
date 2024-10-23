package com.teampatch.feature.profile.edit

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
import androidx.compose.runtime.getValue
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
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.previewPlaceholder
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.domain.model.Image
import com.teampatch.feature.profile.edit.model.ProfileEditErrorHandler
import com.teampatch.feature.profile.edit.model.ProfileEditUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ProfileEditRoute(
    onCompleteRequest: () -> Unit,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel()
) {
    val profileEditUiState by profileEditViewModel.profileEditUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                profileEditViewModel.updateProfileImage(uri)
                return@rememberLauncherForActivityResult
            }
            Toast.makeText(context, "이미지 불러오는 도중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
        }
    )

    when (val uiState = profileEditUiState) {
        is ProfileEditUiState.Error -> {
            Toast.makeText(context, "유저 정보를 불러오는 도중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
        }

        is ProfileEditUiState.Init -> {}

        is ProfileEditUiState.Success -> {
            ProfileEditScreen(
                onBackRequest = onCompleteRequest,
                onEditClick = { profileEditViewModel.editProfile(); onCompleteRequest() },
                onRelationChange = profileEditViewModel::updateRelation,
                onNameChange = profileEditViewModel::updateName,
                onProfileImageRequest = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                profileEditUiState = uiState
            )
        }
    }

    LaunchedEffect(Unit) {
        profileEditViewModel.errorHandler.distinctUntilChanged().collect {
            when (it) {
                is ProfileEditErrorHandler.ProfileEditError -> {
                    Toast.makeText(context, "프로필 수정 중에 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun ProfileEditScreen(
    onBackRequest: () -> Unit,
    onEditClick: () -> Unit,
    onRelationChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onProfileImageRequest: () -> Unit,
    profileEditUiState: ProfileEditUiState.Success
) {
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
            DefaultButton(
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp),
                enable = with(profileEditUiState) { relation.isNotBlank() && name.isNotBlank() }
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
                    .nonReplyClickable {
                        onProfileImageRequest()
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = when (val profileImage = profileEditUiState.profileImage) {
                            is Image.Uri -> profileImage.uri
                            is Image.Url -> profileImage.url
                            null -> ic_my_appbar
                        },
                        placeholder = previewPlaceholder(ic_my_appbar),
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
                        tint = WH,
                    )
                }
            }
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
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp)
            )
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
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileEditScreenPreview() {
    HarmonyTheme {
        ProfileEditScreen(
            onBackRequest = {},
            onEditClick = { },
            onRelationChange = { },
            onNameChange = { },
            onProfileImageRequest = { },
            profileEditUiState = ProfileEditUiState.Success(
                relation = "",
                name = "",
                profileImage = Image.Url("https://picsum.photos/200/300")
            )
        )
    }
}