package com.teampatch.feature.memorystorage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.designsystem.R.drawable
import com.teampatch.core.designsystem.R.drawable.btn_search
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.TempMemoryCard
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.feature.memorystorage.R.string.text_day_datetime
import com.teampatch.feature.memorystorage.R.string.text_month_datetime
import com.teampatch.feature.memorystorage.R.string.text_year_datetime
import java.time.LocalDateTime
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun MemoryStorageRoute(
    onDetailPageRequest: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MemoryStorageViewModel = hiltViewModel()
    val memoryStorageUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val memoryCards: LazyPagingItems<MemoryCard> =
        viewModel.memoryCards.collectAsLazyPagingItems()

    MemoryStorageScreen(
        onDetailPageRequest = onDetailPageRequest,
        memoryCardsLazyItems = memoryCards,
        onSearchQueryChanged = { viewModel.updateSearchQuery(it) }
    )
}

@Composable
internal fun MemoryStorageScreen(
    onDetailPageRequest: () -> Unit,
    memoryCardsLazyItems: LazyPagingItems<MemoryCard>,
    onSearchQueryChanged: (String) -> Unit,
) {
    // 상태 관리
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedSortOption by remember { mutableStateOf("오래된순") }

    Scaffold(
        topBar = {
            Column {
                // AppBar 영역
                AppBar(
                    navigation = {
                        if (isSearchMode) {
                            // 검색 모드: TextField 표시
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = searchText,
                                    onValueChange = {
                                        searchText = it
                                        onSearchQueryChanged(it)
                                    },
                                    placeholder = { Text("검색어를 입력하세요") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = MainGreen,
                                        unfocusedIndicatorColor = Color.Gray
                                    )
                                )
                                Text(
                                    text = "취소",
                                    color = MainGreen,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .clickable {
                                            isSearchMode = false
                                            searchText = ""
                                        }
                                )
                            }
                        } else {
                            // 기본 모드: 제목 텍스트 표시
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = BL)) {
                                        append(stringArrayResource(id = R.array.text_title_appbar)[0])
                                    }
                                    withStyle(style = SpanStyle(color = MainGreen)) {
                                        append(stringArrayResource(id = R.array.text_title_appbar)[1])
                                    }
                                },
                                fontFamily = PretendardFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        }
                    },
                    actions = {
                        if (!isSearchMode) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                // 검색 버튼
                                Image(
                                    painter = painterResource(btn_search),
                                    contentDescription = "search",
                                    modifier = Modifier
                                        .clickable {
                                            isSearchMode = true
                                        }
                                )
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                SortDropdown(
                    selectedOption = selectedSortOption,
                    onOptionSelected = { selectedSortOption = it }
                )
            }
        }
    ) { scaffoldPaddingValues ->
        val sortedItems = remember(memoryCardsLazyItems.itemSnapshotList.items, selectedSortOption) {
            val items = memoryCardsLazyItems.itemSnapshotList.items
            when (selectedSortOption) {
                "최신순" -> items.sortedByDescending { it.dateTime }
                "이름순" -> items.sortedBy { it.text }
                else -> items.sortedBy { it.dateTime }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
        ) {
            items(
                count = sortedItems.size,
                key = { index -> sortedItems[index].id }, // 고유 ID가 있다면 사용
                span = { index ->
                    if (index == 0) {
                        GridItemSpan(maxLineSpan)
                    } else {
                        GridItemSpan(1)
                    }
                }
            ) { index ->
                val card = sortedItems[index]
                TempMemoryCard(
                    title = card.text,
                    description = with(card.dateTime) {
                        "${this?.year}${stringResource(text_year_datetime)} " +
                            "${this?.monthValue}${stringResource(text_month_datetime)} " +
                            "${this?.dayOfMonth}${stringResource(text_day_datetime)}"
                    },
                    painter = painterResource(id = drawable.img_test_memory_card),
                    modifier = Modifier.noRippleClickable {
                        onDetailPageRequest()
                    }
                )
            }
        }
    }
}

@Composable
fun SortDropdown(
    selectedOption: String,
    options: List<String> = listOf("오래된순", "최신순", "이름순"),
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 1.dp) // AppBar와의 간격 추가
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd // Box의 콘텐츠를 오른쪽 끝에 정렬
    ) {
        // 텍스트 + 화살표 아이콘
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
        ) {
            Text(
                text = selectedOption,
                color = MainGreen,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MainGreen
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryStorageScreenPreview() {
    HarmonyTheme {
        MemoryStorageScreen(
            onDetailPageRequest = { },
            flowOf(
                PagingData.from(
                    listOf(
                        MemoryCard(
                            id = "1",
                            writerTitle = "손자",
                            writerName = "김민준",
                            text = "title",
                            imageUrl = "",
                            dateTime = LocalDateTime.now()
                        ),
                        MemoryCard(
                            id = "2",
                            writerTitle = "할머니",
                            writerName = "이영희",
                            text = "어릴 적 사진",
                            imageUrl = "",
                            dateTime = LocalDateTime.now()
                        ),
                        MemoryCard(
                            id = "3",
                            writerTitle = "할머니",
                            writerName = "이영희",
                            text = "어릴 적 사진",
                            imageUrl = "",
                            dateTime = LocalDateTime.now()
                        ),
                        MemoryCard(
                            id = "4",
                            writerTitle = "할머니",
                            writerName = "이영희",
                            text = "어릴 적 사진",
                            imageUrl = "",
                            dateTime = LocalDateTime.now()
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
            onSearchQueryChanged = {}
        )
    }
}