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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.teampatch.core.common.getOrNull
import com.teampatch.core.designsystem.R.drawable.ic_daily_edit
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G3
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.domain.fake.FakeDailyManage
import com.teampatch.core.domain.model.DailyManage
import com.teampatch.feature.daily.expand.model.DailyExpandSideEffect
import com.teampatch.feature.daily.expand.model.DailyExpandUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun DailyExpandRoute(
    onBackRequest: () -> Unit,
    onEditClick: (DailyManage) -> Unit,
    onDeleteClick: (DailyManage) -> Unit,
    viewModel: DailyExpandViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState: DailyExpandUiState by viewModel.dailyExpandUiState

    if (!uiState.isLoading) {
        DailyExpandScreen(
            onBackRequest = onBackRequest,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            uiState = uiState
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is DailyExpandSideEffect.LoadError ->
                    Toast.makeText(context, "데이터를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
internal fun DailyExpandScreen(
    onBackRequest: () -> Unit,
    onEditClick: (DailyManage) -> Unit,
    onDeleteClick: (DailyManage) -> Unit,
    uiState: DailyExpandUiState,
) {
    val daily = uiState.dailyManage.collectAsLazyPagingItems()
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
                .padding(top = 16.dp)
        ) {
            items(daily.itemCount) { index ->
                val item = daily.getOrNull(index) ?: return@items
                DailyItem(
                    dailyItem = daily.getOrNull(index),
                    onEditClick = { daily.getOrNull(index)?.let { onEditClick(it) } },
                    onDeleteClick = { daily.getOrNull(index)?.let { onDeleteClick(it) } }
                )
            }
        }
    }
}

@Composable
fun DailyItem(
    dailyItem: DailyManage?,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    if (dailyItem == null) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(G1, RoundedCornerShape(10.dp))
                .clickable { expanded = !expanded } // 클릭 시 expanded 상태 변경
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "#${dailyItem.number}",
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = G4
                )
                Icon(
                    painter = painterResource(id = ic_daily_edit),
                    contentDescription = "daily_edit",
                    tint = G3,
                    modifier = Modifier.clickable { expanded = !expanded } // 클릭하면 메뉴 활성화
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            ) {
                Text(
                    text = dailyItem.title,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = BL,
                    modifier = Modifier.widthIn(max = 240.dp)
                )
            }
            // DropdownMenu 추가
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(250.dp, -80.dp), // 오른쪽으로 이동
                modifier = Modifier.background(Color.White)

            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "일과 수정",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        expanded = false
                        onEditClick()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            "일과 수정",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        expanded = false
                        onDeleteClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DailyExpandScreenPreview() {
    HarmonyTheme {
        DailyExpandScreen(
            onBackRequest = {},
            onEditClick = {},
            onDeleteClick = {},
            uiState = DailyExpandUiState(dailyManage = flowOf(PagingData.from(listOf(FakeDailyManage().get()))))
        )
    }
}