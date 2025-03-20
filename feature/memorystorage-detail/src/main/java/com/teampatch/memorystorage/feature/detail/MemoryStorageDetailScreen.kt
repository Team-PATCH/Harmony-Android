package com.teampatch.memorystorage.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.MemoryInfoView
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.feature.memorystorage.detail.R.string.btn_look_all_answer
import com.teampatch.feature.memorystorage.detail.R.string.dropdown_edit_answer

@Composable
internal fun MemoryStorageDetailRoute(
    memoryStorageDetailViewModel: MemoryStorageDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = memoryStorageDetailViewModel.uiState
}

@Composable
internal fun MemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    uiState: MemoryStorageDetailUiState,
) {
    var answerEditMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                actions = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(36.dp)
                            .noRippleClickable {
                                answerEditMenuExpanded = true
                            }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_more_question),
                            contentDescription = "more"
                        )
                    }
                    DropdownMenu(
                        expanded = answerEditMenuExpanded,
                        onDismissRequest = { answerEditMenuExpanded = false },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .widthIn(min = 200.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = stringResource(dropdown_edit_answer),
                                        fontFamily = PretendardFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        color = BL
                                    )
                                }
                            },
                            onClick = {
                                answerEditMenuExpanded = false
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            DefaultButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text(stringResource(btn_look_all_answer))
            }
        },
        modifier = Modifier
            .background(WH)
    ) { scaffoldPaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(G1)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_camera_memory),
                    contentDescription = "camera"
                )
            }

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            MemoryInfoView(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "민준갓",
                description = "소리쳐"
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 32.dp, top = 24.dp)
                    .background(G1, RoundedCornerShape(10.dp))
                    .padding(24.dp)
            ) {
                Text(
                    text = "엔믹스엔믹스\n엔믹스엔믹스\n엔믹스엔믹스\n엔믹스엔믹스\n",
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = G5
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemoryStorageDetailScreenPreview() {
    HarmonyTheme {
        MemoryStorageDetailScreen(
            onBackRequest = {},
            uiState = MemoryStorageDetailUiState()
        )
    }
}