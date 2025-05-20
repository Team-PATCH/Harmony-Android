package com.teampatch.daily.certify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R.drawable.img_upload_cert
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.domain.model.DailyComment
import com.teampatch.feature.daily.certify.R.string.text_title_appbar
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch

@Composable
internal fun DailyCertifyRoute(
    onBackRequest: () -> Unit,
) {
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DailyCertifyScreen(
    uiState: DailyCertifyUiState,
    onBackRequest: () -> Unit,
    onCommentSubmit: (String) -> Unit,
    onCommentEditRequest: (DailyComment?) -> Unit,
    onCommentEditComplete: (String) -> Unit,
    onCertifyComplete: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (uiState.editingComment != null) {
        ModalBottomSheet(
            onDismissRequest = { onCommentEditRequest(null) },
            sheetState = sheetState
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(text = "댓글 수정", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                var text by remember { mutableStateOf(uiState.editingComment.content) }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                DefaultButton(onClick = {
                    onCommentEditComplete(text)
                    scope.launch { sheetState.hide() }
                }) {
                    Text("수정 완료")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                title = {
                    Text(stringResource(text_title_appbar))
                }
            )
        },
        bottomBar = {
            when (uiState.certifyStatus) {
                CertifyStatus.BEFORE -> {
                    DefaultButton(
                        onClick = onCertifyComplete,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text("인증 완료")
                    }
                }

                CertifyStatus.PENDING -> {
                    DefaultButton(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        enabled = false
                    ) {
                        Text("처리 중...")
                    }
                }

                CertifyStatus.CONFIRMED -> {
                    Column(Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("댓글을 입력해주세요") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DefaultButton(onClick = { onCommentSubmit("작성한 댓글") }) {
                            Text("댓글 남기기")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // 인증 이미지
            uiState.imageUrl?.let {
                CertifyImage(imageUrl = it)
            }

            Spacer(Modifier.height(12.dp))
            MissionInfoSection(
                missionText = uiState.missionText,
                missionTime = uiState.missionTime
            )

            when (uiState.certifyStatus) {
                CertifyStatus.CONFIRMED -> {
                    // 댓글 목록
                    LazyColumn {
                        items(uiState.comments) { comment ->
                            CommentItem(
                                comment = comment,
                                onEditClick = { onCommentEditRequest(comment) }
                            )
                        }
                    }
                }

                CertifyStatus.BEFORE -> {
                    // 아무것도 안 보여도 됨, 또는 안내 메시지
                    Spacer(modifier = Modifier.height(32.dp))
                }

                CertifyStatus.PENDING -> {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun MissionInfoSection(
    missionText: String,
    missionTime: LocalTime?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // ✅ 가운데 정렬 핵심
    ) {
        Text(
            text = missionText,
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BL
        )

        missionTime?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                text = it.format(DateTimeFormatter.ofPattern("a h:mm")),
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MainGreen
            )
        }
    }
}

@Composable
fun CertifyImage(imageUrl: String?) {
    val isPreview = LocalInspectionMode.current

    Image(
        painter = if (isPreview) {
            painterResource(id = img_upload_cert)
        } else {
            rememberAsyncImagePainter(imageUrl)
        },
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CommentItem(
    comment: DailyComment,
    onEditClick: () -> Unit,
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = comment.writerName, fontWeight = FontWeight.Bold)
        Text(text = comment.content, modifier = Modifier.padding(top = 4.dp))
        TextButton(onClick = onEditClick) {
            Text("댓글 수정")
        }
    }
}

@Preview(name = "1. 인증 전 (작성 전)", showBackground = true)
@Composable
private fun DailyCertifyScreenPreview_Initial() {
    HarmonyTheme {
        DailyCertifyScreen(
            uiState = DailyCertifyUiState(
                missionText = "공원 산책 가서 비둘기 사진 찍기",
                missionTime = LocalTime.of(14, 30),
                imageUrl = "", // 아직 사진 없음
                comments = emptyList(),
                editingComment = null
            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {}
        )
    }
}

@Preview(name = "2. 인증 완료 (댓글 없음)", showBackground = true)
@Composable
private fun DailyCertifyScreenPreview_Completed_NoComment() {
    HarmonyTheme {
        DailyCertifyScreen(
            uiState = DailyCertifyUiState(
                missionText = "공원 산책 가서 비둘기 사진 찍기",
                missionTime = LocalTime.of(14, 30),
                imageUrl = "",
                comments = emptyList(),
                editingComment = null
            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {}
        )
    }
}

@Preview(name = "3. 인증 완료 (댓글 있음)", showBackground = true)
@Composable
private fun DailyCertifyScreenPreview_Completed_WithComment() {
    HarmonyTheme {
        DailyCertifyScreen(
            uiState = DailyCertifyUiState(
                missionText = "공원 산책 가서 비둘기 사진 찍기",
                missionTime = LocalTime.of(14, 30),
                imageUrl = "",
                comments = listOf(
                    DailyComment("1", "순대 조던", "비둘기 너무 귀여워요", ""),
                    DailyComment("2", "김소라", "부산 날씨 완전 봄이야", "")
                ),
                editingComment = null
            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {}
        )
    }
}