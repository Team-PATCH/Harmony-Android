package com.teampatch.feature.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.img_test_memory_card
import com.teampatch.core.designsystem.component.AdditionMemoryCard
import com.teampatch.core.designsystem.component.CollapseMemoryCard
import com.teampatch.core.designsystem.component.DailyRoutineCard
import com.teampatch.core.designsystem.component.ExpandMemoryCard
import com.teampatch.core.designsystem.component.HomeAppBar
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.dialog.MemoryCardCreationDialog
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.designsystem.preview.TodoPreviewParameterProvider
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.model.Todo
import com.teampatch.feature.home.model.MemoryCardUiState
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

@Composable
fun MemberHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
    onDailyRoutineCheckChanged: (String, Boolean) -> Unit, // id, checked
    uploadMemoryCardRequest: (String, LocalDateTime, Uri) -> Unit,
    memoryCardUiState: MemoryCardUiState,
    dailyRoutine: LazyPagingItems<CheckableData<Todo>>
) {
    var photoPickerUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            photoPickerUri = uri
        }
    )
    var memoryCardExpanded by rememberSaveable { mutableStateOf(false) }
    var isMemoryCardCreationDialogShow by rememberSaveable { mutableStateOf(false) }

    if (isMemoryCardCreationDialogShow) {
        MemoryCardCreationDialog(
            onDismissRequest = { isMemoryCardCreationDialogShow = false },
            onCompleteRequest = { memories, date ->
                uploadMemoryCardRequest(memories, date, photoPickerUri)
                photoPickerUri = Uri.EMPTY
                isMemoryCardCreationDialogShow = false
            },
            imageRequest = {
                photoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            painter = rememberAsyncImagePainter(model = photoPickerUri)
        )
    }

    Scaffold(
        topBar = {
            HomeAppBar {
                Image(
                    painter = painterResource(ic_my_appbar),
                    contentDescription = "my",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .nonReplyClickable { onUserPageRequest() }
                )
            }
        }
    ) { scaffoldPaddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(G1)
                        .padding(top = 28.dp, bottom = 24.dp)
                        .nonReplyClickable {
                            if (memoryCardExpanded &&
                                memoryCardUiState is MemoryCardUiState.Success
                            ) {
                                onMemoryCardClick(memoryCardUiState.data.id)
                                return@nonReplyClickable
                            }
                            memoryCardExpanded = true
                        }
                ) {
                    when (memoryCardUiState) {
                        is MemoryCardUiState.Success -> {
                            if (memoryCardExpanded) {
                                ExpandMemoryCard(
                                    memoryCardUiState.data.text,
                                    memoryCardUiState.data.dateTime.let {
                                        "${it.year}${stringResource(R.string.year)} " +
                                                "${it.monthValue}${stringResource(R.string.month)} " +
                                                "${it.dayOfMonth}${stringResource(R.string.day)}"
                                    },
                                    painter = painterResource(img_test_memory_card)
                                )
                                return@item
                            }

                            CollapseMemoryCard(
                                title = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = BL)) {
                                        append("오늘의 ")
                                    }
                                    withStyle(style = SpanStyle(color = MainGreen)) {
                                        append("추억카드")
                                    }
                                    withStyle(style = SpanStyle(color = BL)) {
                                        append("가 공유됐어요!")
                                    }
                                },
                                text = "할머니가 확인 후 추억카드가 완성돼요",
                                writer = memoryCardUiState.data.let { "${it.writerTitle} ${it.writerName}" }
                            )
                        }

                        else -> {
                            AdditionMemoryCard(
                                onClick = {
                                    isMemoryCardCreationDialogShow = true
                                },
                                title = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = BL)) {
                                        append("할머니의 ")
                                    }
                                    withStyle(style = SpanStyle(color = MainGreen)) {
                                        append("추억")
                                    }
                                    withStyle(style = SpanStyle(color = BL)) {
                                        append("을 들어볼까요?")
                                    }
                                },
                                text = "아래를 클릭해 카드를 보내드려요.",
                            )
                        }
                    }
                }
            }

            items(dailyRoutine.itemCount) { index ->
                val lastDateTime =
                    if (index > 0) dailyRoutine.peek(index - 1)?.data?.dateTime else null
                val dateTime = dailyRoutine.peek(index)?.data?.dateTime
                val title = dailyRoutine[index]?.data?.title

                if (dateTime?.toLocalDate() != lastDateTime?.toLocalDate()) {
                    Text(
                        text = buildAnnotatedString {
                            runCatching { dailyRoutine[index]?.data?.dateTime }.getOrNull()
                                ?.let {
                                    withStyle(style = SpanStyle(color = MainGreen)) {
                                        append(
                                            "${it.monthValue}${stringResource(R.string.month)} " +
                                                    "${it.dayOfMonth}${stringResource(R.string.day)}"
                                        )
                                    }
                                }
                            withStyle(SpanStyle(BL)) {
                                append(stringResource(R.string.text_daily_routine_time_stamp))
                            }
                        },
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(start = 24.dp, top = 8.dp, bottom = 12.dp)
                    )
                }
                DailyRoutineCard(
                    onCheckedChange = {
                        val data = dailyRoutine[index]?.data ?: return@DailyRoutineCard
                        dailyRoutine.itemSnapshotList.items[index].checked.value = it
                        onDailyRoutineCheckChanged(data.id, it)
                    },
                    checked = dailyRoutine[index]?.checked?.value ?: false,
                    dateTime = dateTime?.stringHour() ?: "",
                    text = title ?: "",
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .nonReplyClickable {
                            val data = dailyRoutine[index]?.data ?: return@nonReplyClickable
                            onDailyRoutineClick(data.id)
                        }
                )
            }

            if (dailyRoutine.itemCount != 0) {
                item {
                    Box(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun MemberHomeScreenPreview(
    @PreviewParameter(provider = TodoPreviewParameterProvider::class, limit = 1)
    todos: List<Todo>
) {
    HarmonyTheme {
        MemberHomeScreen(
            onUserPageRequest = {},
            onDailyRoutineClick = {},
            onMemoryCardClick = {},
            onDailyRoutineCheckChanged = { _, _ -> },
            uploadMemoryCardRequest = { memories, date, uri -> },
            memoryCardUiState = MemoryCardUiState.Success(
                MemoryCard(
                    id = "1",
                    writerTitle = "손자",
                    writerName = "김민준",
                    text = "title",
                    imageUrl = "",
                    dateTime = LocalDateTime.now()
                )
            ),
            dailyRoutine = flowOf(
                PagingData.from(
                    data = TodoPreviewParameterProvider().values.first()
                        .map { CheckableData(it, mutableStateOf(it.isFinished)) },
                ),
            )
                .collectAsLazyPagingItems()
        )
    }
}

@Preview
@Composable
private fun MemberHomeScreenEmptyPreview(
    @PreviewParameter(provider = TodoPreviewParameterProvider::class, limit = 1)
    todos: List<Todo>
) {
    HarmonyTheme {
        MemberHomeScreen(
            onUserPageRequest = {},
            onDailyRoutineClick = {},
            onMemoryCardClick = {},
            onDailyRoutineCheckChanged = { _, _ -> },
            uploadMemoryCardRequest = { memories, date, uri -> },
            memoryCardUiState = MemoryCardUiState.Wait,
            dailyRoutine = flowOf(PagingData.empty<CheckableData<Todo>>())
                .collectAsLazyPagingItems()
        )
    }
}