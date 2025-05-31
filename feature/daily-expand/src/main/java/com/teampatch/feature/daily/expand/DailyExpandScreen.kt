package com.teampatch.feature.daily.expand

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.designsystem.R.drawable.ic_more_question
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.SubRed
import com.teampatch.core.domain.model.Todo
import com.teampatch.feature.daily.expand.R.string.dropdown_delete_daily
import com.teampatch.feature.daily.expand.R.string.dropdown_edit_daily
import com.teampatch.feature.daily.expand.model.DailyExpandEvent
import java.time.LocalDateTime

@Composable
internal fun DailyExpandRoute(
    onBackRequest: () -> Unit,
    onEditDailyRequest: () -> Unit,
    onDeleteDailyRequest: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel: DailyExpandViewModel = hiltViewModel()

    val dailyRoutines = viewModel.dailyRoutine.collectAsLazyPagingItems()

    DailyExpandScreen(
        dailyRoutines = dailyRoutines,
        onBackRequest = onBackRequest,
        onEditRequest = onEditDailyRequest,
        onDeleteRequest = onDeleteDailyRequest
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect {
                when (it) {
                    is DailyExpandEvent.LoadError -> {
                        Toast.makeText(context, "데이터를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
internal fun DailyExpandScreen(
    dailyRoutines: LazyPagingItems<Todo>,
    onBackRequest: () -> Unit,
    onEditRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
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
                .padding(scaffoldPaddingValues)
                .padding(top = 16.dp)
                .fillMaxSize()
        ) {
            items(dailyRoutines.itemCount) { index ->
                dailyRoutines[index]?.let { item ->
                    DailyItem(
                        title = item.title,
                        onEditClick = onEditRequest,
                        onDeleteClick = onDeleteRequest
                    )
                }
            }
        }
    }
}

@Composable
fun DailyItem(
    title: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isDropDownMenuShow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(G1, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = BL,
                    modifier = Modifier.widthIn(max = 240.dp)
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isDropDownMenuShow = true }
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
                        expanded = isDropDownMenuShow,
                        onDismissRequest = { isDropDownMenuShow = false },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.widthIn(min = 200.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = stringResource(dropdown_edit_daily),
                                        fontFamily = PretendardFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        color = BL
                                    )
                                }
                            },
                            onClick = {
                                onEditClick()
                                isDropDownMenuShow = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = stringResource(dropdown_delete_daily),
                                        fontFamily = PretendardFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        color = SubRed
                                    )
                                }
                            },
                            onClick = {
                                onDeleteClick()
                                isDropDownMenuShow = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DailyExpandScreenPreview() {
    val dummyTodos = listOf(
        Todo(
            id = "1",
            dateTime = LocalDateTime.now(),
            title = "샘플 투두 1",
            isFinished = false
        ),
        Todo(
            id = "2",
            dateTime = LocalDateTime.now().plusHours(1),
            title = "샘플 투두 2",
            isFinished = true
        )
    )

    val lazyItems = remember {
        derivedStateOf {
            dummyTodos.map { it } // CheckableData 없음
        }
    }

    HarmonyTheme {
        LazyColumn {
            items(lazyItems.value.size) { index ->
                DailyItem(
                    title = lazyItems.value[index].title,
                    onEditClick = {},
                    onDeleteClick = {}
                )
            }
        }
    }
}