package com.example.daily_manage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.daily_manage.model.DailyManageSideEffect
import com.example.daily_manage.model.DailyManageUiState
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.noRippleClickable
import com.teampatch.core.designsystem.getOrNull
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.G3
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.domain.fake.FakeDaily
import com.teampatch.core.domain.fake.FakeQuestions
import com.teampatch.daily_manage.R
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
    val dailyManageViewModel: DailyManageViewModel = hiltViewModel()
    val uiState: DailyManageUiState by dailyManageViewModel.dailyManageUiState

    if (!uiState.isLoading) {
        DailyManageScreen(
            onBackRequest = onBackRequest,
            editDailyPageRequest = editDailyPageRequest,
            uiState = uiState
        )
    }

    LaunchedEffect(Unit) {
        dailyManageViewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
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
    uiState: DailyManageUiState
) {
    val daily = uiState.daily.collectAsLazyPagingItems()
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
            items(daily.itemCount) {index ->
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

@Preview
@Composable
private fun DailyManageScreenPreview() {
    HarmonyTheme {
        DailyManageScreen(
            onBackRequest = {},
            editDailyPageRequest = {},
            uiState = DailyManageUiState(daily = flowOf(PagingData.from(FakeDaily().get())))
        )
    }
}