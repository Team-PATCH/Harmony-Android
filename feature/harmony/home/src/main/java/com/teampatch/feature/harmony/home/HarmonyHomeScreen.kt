package com.teampatch.feature.harmony.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.img_test_memory_card
import com.teampatch.core.designsystem.component.CollapseMemoryCard
import com.teampatch.core.designsystem.component.DailyRoutineCard
import com.teampatch.core.designsystem.component.EmptyLetterBox
import com.teampatch.core.designsystem.component.ExpandMemoryCard
import com.teampatch.core.designsystem.component.HomeAppBar
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.designsystem.preview.TodoPreviewParameterProvider
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.model.Todo
import com.teampatch.feature.harmony.home.model.HomeErrorHandler
import com.teampatch.feature.harmony.home.model.MemoryCardUiState
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

@Composable
fun HarmonyHomeRoute(
    onUserPageRequest: () -> Unit,
    onDailyRoutineRegisterPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
    harmonyHomeViewModel: HarmonyHomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dailyRoutine = harmonyHomeViewModel.dailyRoutine.collectAsLazyPagingItems()
    val memoryCardUiState by harmonyHomeViewModel.memoryCardUiState.collectAsStateWithLifecycle()
    val errorHandler by harmonyHomeViewModel.errorHandler.collectAsStateWithLifecycle(null)

    HarmonyHomeScreen(
        onUserPageRequest = onUserPageRequest,
        onDailyRoutineRegisterPageRequest = onDailyRoutineRegisterPageRequest,
        onDailyRoutineClick = onDailyRoutineClick,
        onMemoryCardClick = onMemoryCardClick,
        onDailyRoutineCheckChanged = harmonyHomeViewModel::changeDailyRoutine,
        memoryCardUiState = memoryCardUiState,
        dailyRoutine = dailyRoutine,
    )

    LaunchedEffect(errorHandler) {
        when (errorHandler) {
            null -> {}
            is HomeErrorHandler.ChangeDailyRoutineError -> {
                Toast.makeText(context, "Daily Routine Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun HarmonyHomeScreen(
    onUserPageRequest: () -> Unit,
    onDailyRoutineRegisterPageRequest: () -> Unit,
    onDailyRoutineClick: (String) -> Unit, // id
    onMemoryCardClick: (String) -> Unit, // id
    onDailyRoutineCheckChanged: (String, Boolean) -> Unit, // id, checked
    memoryCardUiState: MemoryCardUiState,
    dailyRoutine: LazyPagingItems<CheckableData<Todo>>
) {
    var memoryCardExpanded by rememberSaveable { mutableStateOf(false) }

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
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
                                            append("가 도착했어요")
                                        }
                                    },
                                    text = "어떤 추억인지 확인해 볼까요?",
                                    writer = memoryCardUiState.data.let { "${it.writerTitle} ${it.writerName}" }
                                )
                            }

                            else -> {
                                EmptyLetterBox()
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

            if (dailyRoutine.itemCount == 0) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.text_daily_routine_empty_title),
                        color = G4,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .widthIn(min = 320.dp)
                            .heightIn(48.dp)
                            .background(MainGreen, RoundedCornerShape(999.dp))
                            .nonReplyClickable { onDailyRoutineRegisterPageRequest() }
                    ) {
                        Text(
                            text = stringResource(R.string.btn_daily_routine_empty),
                            color = WH,
                            fontFamily = PretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocalDateTime.stringHour(): String = when (hour) {
    0 -> "${stringResource(R.string.am)} 12시"
    !in 0..12 -> "${stringResource(R.string.pm)} ${hour - 12}${stringResource(R.string.hour)}"
    else -> "${stringResource(R.string.am)} ${hour}${stringResource(R.string.hour)}"
}

@Preview
@Composable
private fun HarmonyHomeScreenPreview(
    @PreviewParameter(provider = TodoPreviewParameterProvider::class, limit = 1)
    todos: List<Todo>
) {
    HarmonyTheme {
        HarmonyHomeScreen(
            onUserPageRequest = {},
            onDailyRoutineRegisterPageRequest = {},
            onDailyRoutineClick = {},
            onMemoryCardClick = {},
            onDailyRoutineCheckChanged = { _, _ -> },
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
private fun HarmonyHomeScreenEmptyPreview(
    @PreviewParameter(provider = TodoPreviewParameterProvider::class, limit = 1)
    todos: List<Todo>
) {
    HarmonyTheme {
        HarmonyHomeScreen(
            onUserPageRequest = {},
            onDailyRoutineRegisterPageRequest = {},
            onDailyRoutineClick = {},
            onMemoryCardClick = {},
            onDailyRoutineCheckChanged = { _, _ -> },
            memoryCardUiState = MemoryCardUiState.Wait,
            dailyRoutine = flowOf(PagingData.empty<CheckableData<Todo>>())
                .collectAsLazyPagingItems()
        )
    }
}