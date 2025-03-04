package com.teampatch.feature.daily.edit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.DefaultTextField
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.fake.FakeDailyManage

@Composable
internal fun DailyEditRoute(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (String) -> Unit,
    viewModel: DailyEditViewModel = hiltViewModel(),
    ) {
    val context = LocalContext.current
    val uiState by viewModel.dailyEditUiState
    if (!uiState.isLoading) {
        DailyEditScreen(
            onDismissRequest = onDismissRequest,
            onCompleteRequest = {
//                onCompleteRequest.save
                onCompleteRequest(it)
            },
            uiState = uiState
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is DailyEditSideEffect.AddDailyError -> {
                    Toast.makeText(context, "서버로 부터 데이터 전송 오류", Toast.LENGTH_SHORT).show()
                }

                is DailyEditSideEffect.LoadError -> {
                    Toast.makeText(context, "데이터를 불러오지 못하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

@Composable
internal fun DailyEditScreen(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (String) -> Unit,
    uiState: DailyEditUiState,
    ) {
    var daily by rememberSaveable { mutableStateOf(uiState.dailyExpand.content)}

    Scaffold(
        topBar = {
            AppBar(
                title = {
                    Text(
                        text = "이거 좋은 방법은 아닌거 같고 uiState써야되나",
                        maxLines = 1,
                        modifier = Modifier
                            .widthIn(max = 240.dp)
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(R.drawable.ic_close_memory_card),
                        contentDescription = "close",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .noRippleClickable(onClick = onDismissRequest)
                    )
                }
            )
        },
        bottomBar = {
            DefaultButton(
                onClick = { onCompleteRequest(daily) },
                enabled = daily.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text(stringResource(R.string.btn_complete_daily))
            }
        }
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues)
                .verticalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
        ) {
            Text(
                text = stringResource(R.string.text_per_daily),
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = MainGreen,
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 36.dp, bottom = 32.dp)
            ) {
                DefaultTextField(
                    value = daily,
                    onValueChange = {
                        if (it.length <= 200) {
                            daily = it
                        }
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
                    modifier = Modifier
                        .fillMaxSize()
                )
                Text(
                    text = stringResource(R.string.text_count_answer, daily.length),
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = G4,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DailyEditScreenPreview() {
    HarmonyTheme {
        DailyEditScreen(
            onDismissRequest = {},
            onCompleteRequest = {},
            uiState = DailyEditUiState(
                dailyExpand = FakeDailyManage().get()
            )
        )
    }
}