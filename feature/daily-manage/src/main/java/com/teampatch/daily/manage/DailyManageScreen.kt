package com.teampatch.daily.manage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.common.getOrNull
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.G3
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.SubRed
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.emptyUser
import com.teampatch.core.domain.fake.FakeDaily
import com.teampatch.core.domain.fake.FakeDailyComments
import com.teampatch.core.domain.model.Role
import com.teampatch.daily.manage.model.AnswerEvent
import com.teampatch.daily.manage.model.CommentEdit
import com.teampatch.daily.manage.model.CommentEvent
import com.teampatch.daily.manage.model.DailyManageSideEffect
import com.teampatch.daily.manage.model.DailyManageUiState
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable

@Serializable
data object DailyManageRoute

@Composable
fun DailyManageRoute(
    onBackRequest: () -> Unit,
    editDailyPageRequest: (String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: DailyManageViewModel = hiltViewModel()
    val uiState: DailyManageUiState by viewModel.uiState

    if (!uiState.isLoading) {
        DailyManageScreen(
            onBackRequest = onBackRequest,
            editDailyPageRequest = editDailyPageRequest,
            {},
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
                is DailyManageSideEffect.AddCommentError ->
                    Toast.makeText(context, "일과 추가 실패", Toast.LENGTH_SHORT).show()

                is DailyManageSideEffect.DeleteCommentError ->
                    Toast.makeText(context, "일과 삭제 실패", Toast.LENGTH_SHORT).show()

                is DailyManageSideEffect.EditCommentError ->
                    Toast.makeText(context, "일과 수정 실패", Toast.LENGTH_SHORT).show()

                is DailyManageSideEffect.LoadError ->
                    Toast.makeText(context, "데이터를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
internal fun DailyManageScreen(
    onBackRequest: () -> Unit,
    editDailyPageRequest: (String) -> Unit,
    answerEventListener: (AnswerEvent) -> Unit,
    commentEventListener: (CommentEvent) -> Unit,
    uiState: DailyManageUiState
) {
    val daily = uiState.daily.collectAsLazyPagingItems()

    var isCommentDialogShow by rememberSaveable { mutableStateOf(false) }
    var isCommentEditDialogShow by rememberSaveable { mutableStateOf<CommentEdit?>(null) }
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true,
//        confirmValueChange = { it != SheetValue.Hidden }
//    )
    val comments = uiState.comment.collectAsLazyPagingItems()
    var answerEventMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var commentEventMenuExpanded by rememberSaveable { mutableStateOf(false) }

    if (isCommentDialogShow) {
        var text by rememberSaveable { mutableStateOf("") }

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    onBackRequest = onBackRequest,
                    title = {
                        Text(stringResource(R.string.text_title_appbar))
                    }
                )
            }
        ) { scaffoldPaddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPaddingValues)
                    .padding(top = 20.dp)
            ) {
                items(daily.itemCount) { index ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                            .background(G2, RoundedCornerShape(10.dp))
                            .noRippleClickable {
                                val id = daily.getOrNull(index)?.id ?: return@noRippleClickable
                                editDailyPageRequest(id)
                            }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                        ) {
                            Text(
                                text = "#${daily.getOrNull(index)?.number}",
                                fontFamily = PretendardFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = G4,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 26.dp, end = 4.dp)
                            )
                            Icon(
                                painter = painterResource(com.teampatch.core.designsystem.R.drawable.ic_chevron_question),
                                contentDescription = "chevron",
                                tint = G3,
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
                                                color = SubRed,
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
                            Text(
                                text = daily.getOrNull(index)?.title ?: "",
                                fontFamily = PretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = BL,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 26.dp, bottom = 20.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun DailyManageScreenPreview() {
    HarmonyTheme {
        DailyManageScreen(
            onBackRequest = {},
            editDailyPageRequest = {},
            commentEventListener = {},
            answerEventListener = {},
            uiState = DailyManageUiState(
                daily = flowOf(PagingData.from(FakeDaily().get())),
                comment = flowOf(PagingData.from(FakeDailyComments().get())),
                user = emptyUser.copy(uid = "uid001", role = Role.VIP),
                isLoading = false
            )
        )
    }
}