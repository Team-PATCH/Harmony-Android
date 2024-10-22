package com.teampatch.feature.family.info

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teampatch.core.designsystem.R.drawable.ic_export_family_info
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.ic_settings_appbar
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.FamilyProfile
import com.teampatch.core.designsystem.component.FamilyRole
import com.teampatch.core.designsystem.component.MainGreenButton
import com.teampatch.core.designsystem.component.RoundButton
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.preview.FamilyInfoPreviewParameterProvider
import com.teampatch.core.designsystem.preview.UserPreviewParameterProvider
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role
import com.teampatch.feature.family.info.model.FamilyInfoErrorHandler
import com.teampatch.feature.family.info.model.FamilyInfoUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun FamilyInfoRoute(
    onSettingsClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    familyInfoViewModel: FamilyInfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val familyInfoUiState by familyInfoViewModel.familyInfoUiState.collectAsStateWithLifecycle()

    when (val uiState = familyInfoUiState) {
        is FamilyInfoUiState.Error -> {
            Toast.makeText(context, "정보를 불러오는 도중에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }

        is FamilyInfoUiState.Init -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is FamilyInfoUiState.Success -> {
            FamilyInfoScreen(
                onInviteClick = familyInfoViewModel::inviteFamily,
                onSettingsClick = onSettingsClick,
                onProfileEditClick = onProfileEditClick,
                familyInfoUiState = uiState
            )
        }
    }

    LaunchedEffect(Unit) {
        familyInfoViewModel.familyInfoErrorHandler.distinctUntilChanged().collect {
            when (it) {
                is FamilyInfoErrorHandler.InviteError -> {
                    Toast.makeText(context, "초대 도중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun FamilyInfoScreen(
    onSettingsClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    onInviteClick: () -> Unit,
    familyInfoUiState: FamilyInfoUiState.Success
) {
    Scaffold(
        topBar = {
            BackButtonAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.text_title_appbar),
                        fontSize = 20.sp,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = BL
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(ic_settings_appbar),
                        contentDescription = "settings",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .nonReplyClickable {
                                onSettingsClick()
                            }
                    )
                }
            )
        },
    ) { scaffoldPaddingValues ->
        Box(
            modifier = Modifier
                .padding(scaffoldPaddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(G1)
                            .padding(vertical = 16.dp, horizontal = 20.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 1.dp, color = G2, shape = RoundedCornerShape(10.dp))
                                .background(color = WH, shape = RoundedCornerShape(10.dp))
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(ic_my_appbar),
                                contentDescription = "profile",
                                modifier = Modifier
                                    .size(72.dp)
                                    .padding(top = 20.dp)
                            )
                            Text(
                                text = familyInfoUiState.user.name,
                                fontSize = 24.sp,
                                fontFamily = PretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = BL,
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 14.dp)
                            )
                            RoundButton(
                                onClick = { onProfileEditClick() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 42.dp)
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.btn_edit_profile),
                                    fontSize = 18.sp,
                                    fontFamily = PretendardFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = WH
                                )
                            }
                        }
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = BL)) {
                                append("${stringResource(R.string.text_title_family)} ")
                            }
                            withStyle(style = SpanStyle(color = MainGreen)) {
                                append(familyInfoUiState.familyInfo.size.toString())
                            }
                        },
                        fontSize = 22.sp,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = BL,
                        modifier = Modifier
                            .padding(top = 36.dp, bottom = 24.dp, start = 20.dp)
                    )
                }

                items(familyInfoUiState.familyInfo) { family ->
                    FamilyProfile(
                        profileImage = painterResource(ic_my_appbar),
                        title = family.title,
                        name = family.name,
                        role = getFamilyRole(family),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    )
                }

                item {
                    Box(modifier = Modifier.height(80.dp))
                }
            }

            MainGreenButton(
                onClick = { onInviteClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Image(
                    painter = painterResource(ic_export_family_info),
                    contentDescription = "invite"
                )
                Text(
                    text = stringResource(R.string.btn_invite_family),
                    modifier = Modifier.padding(start = 18.dp)
                )
            }
        }
    }
}

private fun getFamilyRole(
    family: FamilyInfo
): FamilyRole? = if (family.isManager) {
    FamilyRole.MANAGER
} else if (family.role == Role.VIP) {
    FamilyRole.ADMIN
} else {
    null
}

@Preview
@Composable
private fun FamilyInfoScreenPreview() {
    HarmonyTheme {
        FamilyInfoScreen(
            onInviteClick = {},
            onSettingsClick = {},
            onProfileEditClick = {},
            familyInfoUiState = FamilyInfoUiState.Success(
                user = UserPreviewParameterProvider().values.first(),
                familyInfo = FamilyInfoPreviewParameterProvider().values.first()
            )
        )
    }
}