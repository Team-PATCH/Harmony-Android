package com.teampatch.daily.certify

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.teampatch.core.designsystem.R.drawable.ic_camera_profile
import com.teampatch.core.designsystem.R.drawable.ic_more_question
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.img_upload_cert
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.RoundButton
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.SubRed
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.domain.model.DailyComment
import com.teampatch.feature.daily.certify.R.string.text_float_add_comment
import com.teampatch.feature.daily.certify.R.string.text_title_appbar
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
internal fun DailyCertifyRoute(
    viewModel: DailyCertifyViewModel,
    onBackRequest: () -> Unit,
    onCertifyCompleteRequest: () -> Unit,
    onNavigateToDetailRequest: () -> Unit, // ✅ 추가: 인증 완료 시 이동할 상세 화면
) {
    val uiState by viewModel.uiState

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                viewModel.updateImage(it.toString())
            }
        }
    )

    val hasNavigated = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState.certifyStatus) {
        if (uiState.certifyStatus == CertifyStatus.CONFIRMED && !hasNavigated.value) {
            hasNavigated.value = true
            onNavigateToDetailRequest()
        }
    }

    DailyCertifyScreen(
        uiState = uiState,
        onBackRequest = onBackRequest,
        onCommentEditRequest = { viewModel.editComment(it) },
        onCertifyCompleteRequest = onCertifyCompleteRequest,
        onOpenCommentSheet = { viewModel.openCommentSheet() },
        onImagePickRequest = { launcher.launch("image/*") }
    )
}

@ExperimentalMaterial3Api
@Composable
fun DailyCertifyDetailRoute(
    viewModel: DailyCertifyViewModel,
    onBackRequest: () -> Unit,
) {
    val uiState by viewModel.uiState

    val commentWriteSheetState = rememberModalBottomSheetState()

    LaunchedEffect(uiState.showCommentSheet) {
        if (uiState.showCommentSheet) {
            commentWriteSheetState.show()
        } else {
            commentWriteSheetState.hide()
        }
    }

    DailyCertifyScreen(
        uiState = uiState,
        onBackRequest = onBackRequest,
        onCommentEditRequest = { viewModel.editComment(it) },
        onCertifyCompleteRequest = {}, // ✅ 완료 버튼 제거
        onOpenCommentSheet = { viewModel.openCommentSheet() },
        onImagePickRequest = { } // ✅ 이미지 수정 불가
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DailyCertifyScreen(
    uiState: DailyCertifyUiState,
    onBackRequest: () -> Unit,
    onCommentEditRequest: (DailyComment?) -> Unit,
    onCertifyCompleteRequest: () -> Unit,
    onOpenCommentSheet: () -> Unit,
    onImagePickRequest: () -> Unit, // ✅ 추가
) {
    val commentEditSheetState = rememberModalBottomSheetState()
    val commentWriteSheetState = rememberModalBottomSheetState()
    val isImageUploaded = uiState.imageUrl?.isNotBlank() == true
    val isCertifyConfirmed = uiState.certifyStatus == CertifyStatus.CONFIRMED
    val lazyListState = rememberLazyListState()
    val isFloatingVisible by remember {
        derivedStateOf {
            !lazyListState.isScrollInProgress && !lazyListState.canScrollBackward
        }
    }

    LaunchedEffect(uiState.editingComment) {
        Log.d("DEBUG", "Editing comment: ${uiState.editingComment}")
        if (uiState.editingComment != null) {
            commentEditSheetState.show()
        } else {
            commentEditSheetState.hide()
        }
    }

    LaunchedEffect(uiState.showCommentSheet) {
        if (uiState.showCommentSheet) {
            commentWriteSheetState.show()
        } else {
            commentWriteSheetState.hide()
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
                        onClick = onCertifyCompleteRequest,
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text("인증 완료")
                    }
                }

                uiState.certifyStatus == CertifyStatus.PENDING -> {
                    DefaultButton(
                        onClick = onCertifyCompleteRequest,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text("인증 완료")
                    }
                }

                isCertifyConfirmed -> {
                    // ✅ 인증 완료 시 bottomBar는 비우거나 "제어용 UI"로 대체 가능
                    Spacer(modifier = Modifier.height(1.dp)) // 유지용
                }
            }
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = WH)
            ) {
                when {
                    uiState.imageUrl?.isNotBlank() == true -> {
                        CertifyImage(imageUrl = uiState.imageUrl)
                    }

                    else -> {
                        CertifyImagePlaceholder(onClick = onImagePickRequest)
                    }
                }

                Spacer(Modifier.height(12.dp))

                MissionInfoSection(
                    missionText = uiState.missionText,
                    missionTime = uiState.missionTime
                )

                if (isCertifyConfirmed) {
                    LazyColumn(
                        state = lazyListState, // ✅ 상태 연결
                        modifier = Modifier
                            .fillMaxSize()
                            .background(G1),
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 120.dp // ✅ 댓글 남기기 UI를 위한 padding
                        )
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
                                onDeleteClick = { /* TODO */ }
                            )
                        }
                    }
                }
            }

            // ✅ 댓글 작성 BottomSheet
            if (uiState.showCommentSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        // 닫기 요청 → ViewModel에서 상태 초기화
                        onCommentEditRequest(null)
                    },
                    sheetState = commentWriteSheetState
                ) {
                    CommentWriteSheetContent(
                        onSubmit = { text ->
                            // TODO: ViewModel에 댓글 추가 메서드 연결
                            // viewModel.addComment(...)
                        },
                        onDismiss = { onCommentEditRequest(null) }
                    )
                }
            }

// ✅ 댓글 수정 BottomSheet
            uiState.editingComment?.let { editingComment ->
                ModalBottomSheet(
                    onDismissRequest = {
                        onCommentEditRequest(null)
                    },
                    sheetState = commentEditSheetState
                ) {
                    CommentEditSheetContent(
                        initialText = editingComment.content,
                        onSubmit = { updatedText ->
                            // TODO: ViewModel에 댓글 수정 메서드 연결
                            // viewModel.updateComment(editingComment.commentId, updatedText)
                            onCommentEditRequest(null)
                        },
                        onDismiss = { onCommentEditRequest(null) }
                    )
                }
            }

            // ✅ 댓글 남기기 UI (Floating UI)
            if (isCertifyConfirmed) {
                val shouldShowFloatingCommentButton = isCertifyConfirmed && isFloatingVisible
                // IDE에서 always true로 추론하는 건 Preview 상의 오해임 – 런타임에는 유동적

                AnimatedVisibility(
                    visible = shouldShowFloatingCommentButton,
                    enter = fadeIn(tween(500)),
                    exit = fadeOut(tween(500)),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    RoundButton(
                        onClick = onOpenCommentSheet,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .size(200.dp, 68.dp)
                    ) {
                        Text(
                            text = stringResource(text_float_add_comment),
                            fontSize = 22.sp
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
fun CertifyImagePlaceholder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(G1)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(ic_camera_profile),
            contentDescription = null,
            tint = MainGreen,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "이곳을 눌러 사진을 남겨보세요!",
            color = MainGreen,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// preview용
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

@Composable
fun CommentWriteSheetContent(
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text("댓글 남기기", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = comment,
            onValueChange = { comment = it },
            placeholder = { Text("댓글을 입력해주세요.") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        DefaultButton(
            onClick = { onSubmit(comment) },
            enabled = comment.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("작성 완료")
        }
    }
}

@Composable
fun CommentEditSheetContent(
    initialText: String,
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var comment by remember { mutableStateOf(initialText) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text("댓글 수정", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = comment,
            onValueChange = { comment = it },
            placeholder = { Text("댓글을 입력해주세요.") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        DefaultButton(
            onClick = { onSubmit(comment) },
            enabled = comment.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("수정 완료")
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
                editingComment = null,
                certifyStatus = CertifyStatus.BEFORE
            ),
            onBackRequest = {},
            onCommentEditRequest = {},
            onCertifyCompleteRequest = {},
            onOpenCommentSheet = {},
            onImagePickRequest = {}
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
                imageUrl = "file:///Users/t2023-m0086/Desktop/%E1%84%8F%E1%85%A1%E1%84%85%E1%85%B5%E1%84%82%E1%85%A1.jpg",
                comments = emptyList(),
                editingComment = null,
                certifyStatus = CertifyStatus.PENDING

            ),
            onBackRequest = {},
            onCommentEditRequest = {},
            onCertifyCompleteRequest = {},
            onOpenCommentSheet = {},
            onImagePickRequest = {}

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
                imageUrl = "file:///Users/t2023-m0086/Desktop/%E1%84%8F%E1%85%A1%E1%84%85%E1%85%B5%E1%84%82%E1%85%A1.jpg",
                comments = listOf(
                    DailyComment("1", "순대 조던", "유정상", "비둘기 너무 귀여워요"),
                    DailyComment("2", "김소라", "씹희", "부산 날씨 완전 봄이야")
                ),
                editingComment = null,
                certifyStatus = CertifyStatus.CONFIRMED // ✅ 이걸 넣어야 댓글 목록이 나타남!

            ),
            onBackRequest = {},
            onCommentEditRequest = {},
            onCertifyCompleteRequest = {},
            onOpenCommentSheet = {},
            onImagePickRequest = {}
        )
    }
}