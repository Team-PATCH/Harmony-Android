package com.teampatch.feature.memory.storage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.common.getOrNull
import com.teampatch.core.designsystem.R.drawable
import com.teampatch.core.designsystem.R.drawable.btn_search
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.TempMemoryCard
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.utils.noRippleClickable
import androidx.compose.material3.LargeFloatingActionButton as LargeFloatingActionButton

@Composable
fun MemoryStorageScreen(
    onClick: () -> Unit,
    uiState: MemoryStorageDetailUiState

) {

    val memories = uiState.memoryStorage.collectAsLazyPagingItems()

    // 상태 관리
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedSortOption by remember { mutableStateOf("오래된순") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

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
                                    onValueChange = { searchText = it },
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
                                fontSize = 22.sp,
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

                // 정렬 옵션 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp) // AppBar와의 간격 추가
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterEnd // Box의 콘텐츠를 오른쪽 끝에 정렬
                ) {
                    Text(
                        text = selectedSortOption,
                        color = MainGreen,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            isDropdownExpanded = true
                        }
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        val options = listOf("오래된순", "최신순", "이름순")
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSortOption = option
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                contentColor = Color.Black,
                containerColor = Color.Green,
                onClick = { onClick() },
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        },
    ) { scaffoldPaddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(
            2
        ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
                .noRippleClickable {
                    val id = memories.getOrNull(index)?.id ?: return@noRippleClickable
                    memoryStorageDetailPageRequest(id)
                }
        ) {
            items(
                count = 9,
                key = null,
                span = { index ->
                    if (index == 0) {
                        GridItemSpan(maxLineSpan) // 아이템이 그리드 전체를 차지
                    } else {
                        GridItemSpan(1) // 기본적으로 한 열만 차지
                    }
                }
                ) {
                TempMemoryCard(title = "민준갓", description = "코딩신", painter = painterResource(id = drawable.img_test_memory_card))
            }
        }

    }
}



@Preview
@Composable
private fun MemoryStorageScreenPreview() {
    HarmonyTheme {
        MemoryStorageScreen(
            onClick = {}
        )
    }
}
