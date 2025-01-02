package com.teampatch.feature.question.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.common.getOrNull
import com.teampatch.core.designsystem.R.drawable.ic_more_question
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.DefaultTextField
import com.teampatch.core.designsystem.component.RoundButton
import com.teampatch.core.designsystem.dialog.InputLargeTextBottomSheetContent
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G3
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.SubRed
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.fake.FakeQuestionComments
import com.teampatch.core.domain.fake.FakeQuestionDetail
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import com.teampatch.feature.question.detail.model.AnswerEvent
import com.teampatch.feature.question.detail.model.CommentEdit
import com.teampatch.feature.question.detail.model.CommentEvent
import com.teampatch.feature.question.detail.model.QuestionDetailSideEffect
import com.teampatch.feature.question.detail.model.QuestionDetailUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun QuestionDetailRoute(
    onBackRequest: () -> Unit,
    answerEditPageRequest: () -> Unit,
    viewModel: QuestionDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState

    if (!uiState.isLoading) {
        QuestionDetailScreen(
            onBackRequest = onBackRequest,
            answerEventListener = { answerEditPageRequest() },
            commentEventListener = { event ->
                when (event) {
                    is CommentEvent.Add -> viewModel.addComment(event.commentText)
                    is CommentEvent.Delete -> viewModel.deleteComment(event.commentId)
                    is CommentEvent.Edit -> viewModel.editComment(
                        commentId = event.commentId,
                        text = event.commentText
                    )
                }
            },
            uiState = uiState
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is QuestionDetailSideEffect.AddCommentError ->
                    Toast.makeText(context, "댓글 추가 실패", Toast.LENGTH_SHORT).show()

                is QuestionDetailSideEffect.DeleteCommentError ->
                    Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show()

                is QuestionDetailSideEffect.EditCommentError ->
                    Toast.makeText(context, "댓글 수정 실패", Toast.LENGTH_SHORT).show()

                is QuestionDetailSideEffect.LoadError ->
                    Toast.makeText(context, "데이터를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QuestionDetailScreen(
    onBackRequest: () -> Unit,
    answerEventListener: (AnswerEvent) -> Unit,
    commentEventListener: (CommentEvent) -> Unit,
    uiState: QuestionDetailUiState,
) {
    var isCommentDialogShow by rememberSaveable { mutableStateOf(false) }
    var isCommentEditDialogShow by rememberSaveable { mutableStateOf<CommentEdit?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden }
    )
    val comments = uiState.comment.collectAsLazyPagingItems()
    var answerEventMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var commentEventMenuExpanded by rememberSaveable { mutableStateOf(false) }

    if (isCommentDialogShow) {
        var text by rememberSaveable { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            containerColor = WH,
            dragHandle = null
//            sheetGesturesEnabled = false TODO: androidx.compose.material3:material3:1.4.0-alpha02
        ) {
            InputLargeTextBottomSheetContent(
                onDismissRequest = { isCommentDialogShow = false },
                onCompleteRequest = {
                    commentEventListener(CommentEvent.Add(text))
                    isCommentDialogShow = false
                },
                title = { Text(text = stringResource(R.string.text_title_add_comment)) },
                buttonText = { Text(text = stringResource(R.string.btn_complete_add_comment)) },
                buttonEnable = text.isNotBlank()
            ) {
                CommentEditorContent(
                    text = text
                ) {
                    if (it.length <= 100) {
                        text = it
                    }
                }
            }
        }
    }

    isCommentEditDialogShow?.let { editor ->
        var text by rememberSaveable { mutableStateOf(editor.answer) }

        ModalBottomSheet(
            onDismissRequest = { },
            sheetState = sheetState,
            containerColor = WH,
            dragHandle = null
//            sheetGesturesEnabled = false TODO: androidx.compose.material3:material3:1.4.0-alpha02
        ) {
            InputLargeTextBottomSheetContent(
                onDismissRequest = { isCommentEditDialogShow = null },
                onCompleteRequest = {
                    commentEventListener(CommentEvent.Edit(editor.commentId, text))
                    isCommentEditDialogShow = null
                },
                title = { Text(text = stringResource(R.string.text_title_edit_comment)) },
                buttonText = { Text(text = stringResource(R.string.btn_complete_edit_comment)) },
                buttonEnable = text.isNotBlank()
            ) {
                CommentEditorContent(
                    text = text
                ) {
                    if (it.length <= 100) {
                        text = it
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                actions = {
                    if (uiState.user.role == Role.VIP) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(36.dp)
                                .noRippleClickable {
                                    answerEventMenuExpanded = true
                                }
                        ) {
                            Image(
                                painter = painterResource(ic_more_question),
                                contentDescription = "more"
                            )
                        }
                        DropdownMenu(
                            expanded = answerEventMenuExpanded,
                            onDismissRequest = { answerEventMenuExpanded = false },
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
                                            text = stringResource(R.string.dropdown_edit_answer),
                                            fontFamily = PretendardFontFamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 20.sp,
                                            color = BL
                                        )
                                    }
                                },
                                onClick = {
                                    answerEventListener(AnswerEvent.EDIT)
                                    answerEventMenuExpanded = false
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .background(WH)
            )
        },
        floatingActionButton = {
            RoundButton(
                onClick = { isCommentDialogShow = true },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(200.dp, 68.dp)
            ) {
                Text(
                    text = stringResource(R.string.float_add_comment),
                    fontSize = 22.sp
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { scaffoldPaddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
                .background(G1)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WH)
                        .padding(top = 40.dp)
                ) {
                    Text(
                        text = "${uiState.detail.number}${stringResource(R.string.text_number_question)}",
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = MainGreen,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Text(
                        text = uiState.detail.title,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = BL,
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
                    )
                    Text(
                        text = with(uiState.detail.dateTime) {
                            "${year}${stringResource(R.string.text_year_datetime)} " +
                                "${monthValue}${stringResource(R.string.text_month_datetime)} " +
                                "${dayOfMonth}${stringResource(R.string.text_day_datetime)}"
                        },
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = G3,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 32.dp, top = 24.dp)
                            .background(G1, RoundedCornerShape(10.dp))
                            .padding(24.dp)
                    ) {
                        Text(
                            text = uiState.detail.content,
                            fontFamily = PretendardFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = G5
                        )
                    }
                }
            }

            item {
                Text(
                    text = "${stringResource(R.string.text_count_comment)} ${uiState.detail.commentCount}",
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = G5,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 16.dp)
                )
            }

            items(comments.itemCount) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
                        .background(WH)
                        .padding(all = 20.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(ic_my_appbar),
                                contentDescription = "profile image"
                            )
                            Text(
                                text = comments.getOrNull(index)?.writerName ?: "",
                                fontFamily = PretendardFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                color = G5,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        if (comments.getOrNull(index)?.writerName == uiState.user.name) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterEnd)
                                    .noRippleClickable {
                                        commentEventMenuExpanded = true
                                    }
                            ) {
                                Icon(
                                    painter = painterResource(ic_more_question),
                                    contentDescription = "more",
                                    tint = G5,
                                    modifier = Modifier
                                        .size(width = 4.dp, height = 16.dp)
                                        .align(Alignment.CenterEnd)
                                )
                                DropdownMenu(
                                    expanded = commentEventMenuExpanded,
                                    onDismissRequest = { commentEventMenuExpanded = false },
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
                                                    text = stringResource(R.string.dropdown_edit_comment),
                                                    fontFamily = PretendardFontFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 20.sp,
                                                    color = BL
                                                )
                                            }
                                        },
                                        onClick = {
                                            val comment =
                                                comments.getOrNull(index) ?: return@DropdownMenuItem
                                            isCommentEditDialogShow = CommentEdit(
                                                commentId = comment.commentId,
                                                answer = comment.content
                                            )
                                            commentEventMenuExpanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.dropdown_delete_comment),
                                                    fontFamily = PretendardFontFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 20.sp,
                                                    color = SubRed
                                                )
                                            }
                                        },
                                        onClick = {
                                            comments.getOrNull(index)?.commentId?.let {
                                                commentEventListener(CommentEvent.Delete(it))
                                            }
                                            commentEventMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = comments.getOrNull(index)?.content ?: "",
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = BL,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            item { Box(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ColumnScope.CommentEditorContent(
    text: String,
    onTextChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 12.dp)
            .height(204.dp)
    ) {
        DefaultTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = false,
            maxLines = 6,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = "${text.length}/100${stringResource(R.string.text_per_comment)}",
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = G4,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 16.dp, horizontal = 20.dp)
        )
    }
}

@Preview
@Composable
private fun QuestionDetailScreenPreview() {
    HarmonyTheme {
        QuestionDetailScreen(
            onBackRequest = {},
            answerEventListener = {},
            commentEventListener = {},
            uiState = QuestionDetailUiState(
                detail = FakeQuestionDetail().get(),
                comment = flowOf(PagingData.from(FakeQuestionComments().get())),
                user = User.createEmptyUser().copy(uid = "uid001", role = Role.VIP),
                isLoading = false
            )
        )
    }
}