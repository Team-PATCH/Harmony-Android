package com.teampatch.memorystorage.feature.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.MemoryInfoView
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.feature.memorystorage.detail.R.string.btn_look_all_answer

@Composable
internal fun MemoryStorageDetailRoute(
    memoryStorageDetailViewModel: MemoryStorageDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = memoryStorageDetailViewModel.uiState

    when (uiState.screenState) {
        MemoryDetailScreenState.Detail -> MemoryStorageDetailScreen(
            uiState = uiState,
            onBackRequest = { TODO() },
            onShowConversation = { memoryStorageDetailViewModel.showConversation() },
            onRestartConversation = { TODO() }
        )

        MemoryDetailScreenState.Conversation -> ConversationView(
            onDismiss = { memoryStorageDetailViewModel.showDetail() },
            onRestartConversation = { TODO() }
        )
    }
}

@Composable
fun ConversationView(
    onDismiss: () -> Unit,
    onRestartConversation: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 24.dp)
    ) {
        // 상단 날짜 + 제목 + 닫기 버튼
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "다은이 태어난 날",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFECECEC))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("2024년 5월 4일", fontSize = 12.sp)
                }
            }

            IconButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "닫기")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 프로필 이미지 + 질문 + 사진
        Row(verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(id = R.drawable.ic_my_appbar), // 귀여운 캐릭터 아이콘 대체
                contentDescription = "캐릭터",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_my_appbar), // 병실 이미지 대체
                    contentDescription = "병실 사진",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color(0xFF4A90E2), RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier.padding(end = 32.dp)
                ) {
                    Text(
                        text = "다은이를 분만실에서 처음 봤을 때 어떤 느낌이 들었나요?",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 대답 말풍선
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFD9FDD3), // 연한 초록
                modifier = Modifier.padding(start = 64.dp)
            ) {
                Text(
                    text = "너무 사랑스러웠단다. 내 소중한 손녀 딸을 보고 싶었거든 어쩌구 저쩌구 그래서 울산 병원에서 어쩌구 저쩌구",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 다시 대화하기 버튼
        Button(
            onClick = onRestartConversation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2DC26B))
        ) {
            Text("다시 대화하기", color = Color.White)
        }
    }
}

@Composable
internal fun MemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    onShowConversation: () -> Unit,
    onRestartConversation: () -> Unit,
    uiState: MemoryStorageDetailUiState,
) {
    var answerEditMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
                                showBottomSheet = true
                            }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_more_question),
                            contentDescription = "more"
                        )
                    }
                }
            )
        },
        bottomBar = {
            DefaultButton(
                onClick = { onShowConversation() },
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
    BottomSheetForMemory(
        showBottomSheet = showBottomSheet,
        onDismiss = { showBottomSheet = false },
        onRestartConversation = {
            showBottomSheet = false
            onRestartConversation()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForMemory(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onRestartConversation: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "다시 대화하기",
                    fontSize = 34.sp,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 22.dp)
                        .clickable { onRestartConversation() }
                )
                HorizontalDivider()

                Text(
                    text = "삭제하기",
                    fontSize = 34.sp,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 23.dp)
                        .clickable { TODO() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemoryStorageDetailScreenPreview() {
//    HarmonyTheme {
//        MemoryStorageDetailScreen(
//            onBackRequest = {},
//            uiState = MemoryStorageDetailUiState(),
//            onRestartConversation = {},
//            onShowConversation = {},
//        )
//    }
    val fakeUiState = MemoryStorageDetailUiState(screenState = MemoryDetailScreenState.Conversation)

    when (fakeUiState.screenState) {
        MemoryDetailScreenState.Detail -> MemoryStorageDetailScreen(
            uiState = fakeUiState,
            onBackRequest = {},
            onShowConversation = {},
            onRestartConversation = {}
        )

        MemoryDetailScreenState.Conversation -> ConversationView(
            onDismiss = {},
            onRestartConversation = {}
        )
    }
}