package com.teampatch.memorystorage.feature.detail

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.MemoryInfoView
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.model.MemoryCardQuestion
import com.teampatch.feature.memorystorage.detail.R.string.btn_look_all_answer
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun MemoryStorageDetailRoute(
    onBackRequest: () -> Unit,
    onShowConversation: () -> Unit,
    onRestartConversation: () -> Unit,
    deleteCardRequest: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel: MemoryStorageDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MemoryStorageDetailUiState.Loading -> {
            // TODO: 로딩 UI
        }

        is MemoryStorageDetailUiState.Error -> {
            // TODO: 에러 UI
        }

        is MemoryStorageDetailUiState.Success -> {
            val state = uiState as MemoryStorageDetailUiState.Success
            when (state.screenState) {
                MemoryStorageDetailScreenState.Detail -> {
                    MemoryStorageDetailScreen(
                        memoryStorageDetailUiState = state,
                        onBackRequest = onBackRequest,
                        onShowConversation = viewModel::loadConversation,
                        onRestartConversation = onRestartConversation,
                        deleteCardRequest = {
                            viewModel.deleteMemoryCard()
                        }
                    )
                }

                MemoryStorageDetailScreenState.Conversation -> {
                    ConversationView(
                        onDismiss = viewModel::showDetail,
                        onRestartConversation = {},
                        memoryCard = state.memoryCard,
                        question = state.question ?: MemoryCardQuestion("질문 없음") // fallback
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    is MemoryStorageDetailEvent.Deleted -> {
                        deleteCardRequest()
                    }

                    is MemoryStorageDetailEvent.DeleteError -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }

                    is MemoryStorageDetailEvent.Conversation -> {
                        onShowConversation()
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun ConversationView(
    onDismiss: () -> Unit,
    onRestartConversation: () -> Unit,
    memoryCard: MemoryCard,
    question: MemoryCardQuestion,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = memoryCard.writerTitle,
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
                    Text(
                        text = memoryCard.dateTime.toFormattedString(),
                        fontSize = 12.sp)
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
                        text = question.question,
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
                    text = memoryCard.text,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 다시 대화하기 버튼
        Button(
            onClick = { onRestartConversation() },
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
    memoryStorageDetailUiState: MemoryStorageDetailUiState.Success,
    onShowConversation: () -> Unit,
    onRestartConversation: () -> Unit,
    deleteCardRequest: (String) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val memory = memoryStorageDetailUiState.memoryCard

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
                modifier = Modifier.fillMaxWidth(),
                title = memory?.writerTitle ?: "",
                dateTime = memory?.dateTime.toString(),
                circleTexts = listOf(
                    memory?.writerName ?: "",
                    memory?.dateTime?.toFormattedString() ?: "",
                    memory?.text ?: ""
                )
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
                    text = memory?.text ?: "",
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
        },
        memoryCardId = memory?.id ?: "", // ✅ null-safe로 전달
        deleteCardRequest = deleteCardRequest
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForMemory(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onRestartConversation: () -> Unit,
    memoryCardId: String,
    deleteCardRequest: (String) -> Unit,
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
                        .clickable {
                            deleteCardRequest(memoryCardId)
                        }
                )
            }
        }
    }
}

fun LocalDateTime.toFormattedString(): String {
    val formatter = DateTimeFormatter.ofPattern("M월")
    return this.format(formatter)
}

@Preview
@Composable
private fun MemoryStorageDetailScreenPreview() {
    HarmonyTheme {
        MemoryStorageDetailScreen(
            onBackRequest = {},
            memoryStorageDetailUiState = MemoryStorageDetailUiState.Success(
                memoryCard = MemoryCard(
                    id = "1",
                    writerTitle = "손자",
                    writerName = "김민준",
                    text = "다은아 다은아 헌집 줄게 새집 다오...",
                    imageUrl = "",
                    dateTime = LocalDateTime.of(2024, 5, 4, 15, 0)
                ),
                screenState = MemoryStorageDetailScreenState.Detail
            ),
            onRestartConversation = {},
            onShowConversation = {},
            deleteCardRequest = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryStorageDetailScreen_ConversationPreview() {
    HarmonyTheme {
        ConversationView(
            onDismiss = {},
            onRestartConversation = {}
        )
    }
}