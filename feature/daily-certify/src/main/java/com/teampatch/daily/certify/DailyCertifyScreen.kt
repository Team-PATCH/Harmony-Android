package com.teampatch.daily.certify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.teampatch.core.designsystem.R.drawable.ic_more_question
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.img_upload_cert
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.SubRed
import com.teampatch.core.designsystem.theme.WH
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
    onDismissDialog: () -> Unit,
    onOpenCommentSheet: () -> Unit,
    onCloseCommentSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isImageUploaded = uiState.imageUrl?.isNotBlank() == true
    val isCertifyConfirmed = uiState.certifyStatus == CertifyStatus.CONFIRMED

    // ✅ 댓글 수정 BottomSheet
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

    // ✅ 사진 인증 안내 Dialog
    if (uiState.showCertifyDialog) {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            confirmButton = {
                TextButton(onClick = onDismissDialog) {
                    Text("확인")
                }
            },
            title = { Text("사진 인증 안내") },
            text = { Text("이 사진이 정말 맞나요?") }
        )
    }

    // ✅ 댓글 작성 BottomSheet
    if (uiState.showCommentSheet) {
        ModalBottomSheet(
            onDismissRequest = onCloseCommentSheet,
            sheetState = sheetState
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("댓글 작성", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("댓글을 입력해주세요") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                DefaultButton(onClick = {
                    onCommentSubmit(text)
                    scope.launch { sheetState.hide() }
                    onCloseCommentSheet()
                }) {
                    Text("댓글 남기기")
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
            when {
                !isImageUploaded -> {
                    DefaultButton(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text("인증 완료")
                    }
                }

                uiState.certifyStatus == CertifyStatus.BEFORE -> {
                    DefaultButton(
                        onClick = onCertifyComplete,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text("인증 완료")
                    }
                }

                isCertifyConfirmed -> {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDismissDialog() }, // 아이콘 클릭 시 Dialog 띄우기
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "댓글을 남겨보세요!")
                            Icon(Icons.Default.MoreVert, contentDescription = "댓글 안내")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        DefaultButton(onClick = onOpenCommentSheet) {
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
                .background(color = WH)
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

            if (isCertifyConfirmed) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(G1),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    item {
                        Text(
                            text = "댓글 ${uiState.comments.size}",
                            fontFamily = PretendardFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = G5,
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                    }

                    items(uiState.comments, key = { it.commentId }) { comment ->
                        DailyCertifyCommentItem(
                            comment = comment,
                            onEditClick = { onCommentEditRequest(comment) },
                            onDeleteClick = { /* TODO: 삭제 핸들러 추가 */ }
                        )
                    }
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
fun DailyCertifyCommentItem(
    comment: DailyComment,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .background(color = WH)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: 실제 사용자 프로필 이미지 연결 필요
            Image(
                painter = painterResource(ic_my_appbar),
                contentDescription = "user profile"
            )

            Text(
                text = comment.writerName,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = G5,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(ic_more_question),
                        contentDescription = "더보기 메뉴",
                        tint = G5
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("수정", fontSize = 16.sp) },
                        onClick = {
                            onEditClick()
                            showMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("삭제", fontSize = 16.sp, color = SubRed) },
                        onClick = {
                            onDeleteClick()
                            showMenu = false
                        }
                    )
                }
            }
        }

        Text(
            text = comment.content,
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = BL,
            modifier = Modifier.padding(top = 12.dp)
        )
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
                editingComment = null,
                certifyStatus = CertifyStatus.BEFORE
            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {},
            onDismissDialog = {},
            onOpenCommentSheet = {},
            onCloseCommentSheet = {}
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
                editingComment = null,
                certifyStatus = CertifyStatus.PENDING

            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {},
            onDismissDialog = {},
            onOpenCommentSheet = {},
            onCloseCommentSheet = {}
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
                    DailyComment("1", "순대 조던", "유정상", "비둘기 너무 귀여워요"),
                    DailyComment("2", "김소라", "씹희", "부산 날씨 완전 봄이야")
                ),
                editingComment = null,
                certifyStatus = CertifyStatus.CONFIRMED // ✅ 이걸 넣어야 댓글 목록이 나타남!

            ),
            onBackRequest = {},
            onCommentSubmit = {},
            onCommentEditRequest = {},
            onCommentEditComplete = {},
            onCertifyComplete = {},
            onDismissDialog = {},
            onOpenCommentSheet = {},
            onCloseCommentSheet = {}
        )
    }
}