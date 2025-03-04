package com.teampatch.feature.daily.edit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.core.domain.fake.FakeDailyManage
import com.teampatch.feature.daily.edit.R.string.btn_complete_daily
import com.teampatch.feature.daily.edit.R.string.text_alarm_time
import com.teampatch.feature.daily.edit.R.string.text_per_daily
import com.teampatch.feature.daily.edit.R.string.title_daily
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DailyEditScreen(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (String) -> Unit,
    uiState: DailyEditUiState,
) {
    var daily by rememberSaveable { mutableStateOf(uiState.dailyExpand.content) }
    var dateTime: LocalDateTime? by rememberSaveable { mutableStateOf(null) }
    var isDatePickerDialogShow by rememberSaveable { mutableStateOf(false) }

    if (isDatePickerDialogShow) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { isDatePickerDialogShow = false },
            confirmButton = {
                Text(
                    text = stringResource(R.string.date_picker_ok),
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 12.dp, end = 12.dp)
                        .noRippleClickable {
                            val dateMillis =
                                datePickerState.selectedDateMillis ?: return@noRippleClickable
                            val instant = Instant.ofEpochMilli(dateMillis)
                            dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
                            isDatePickerDialogShow = false
                        }
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.date_picker_cancel),
                    modifier = Modifier.noRippleClickable { isDatePickerDialogShow = false }
                )
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = { Text(stringResource(title_daily)) },
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
                Text(stringResource(btn_complete_daily))
            }
        }
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .padding(scaffoldPaddingValues)
                .height(IntrinsicSize.Max)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(top = 24.dp, bottom = 14.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(text_per_daily),
                fontFamily = PretendardFontFamily,
                color = BL,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp)
            )

            Box(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 36.dp, bottom = 32.dp)
            ) {
                DefaultTextField(
                    value = daily,
                    onValueChange = { if (it.length <= 200) daily = it },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
                    modifier = Modifier.height(IntrinsicSize.Max)
                )
            }

            Text(
                text = stringResource(text_alarm_time),
                fontFamily = PretendardFontFamily,
                color = BL,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .background(color = WH, shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = G2, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp)
                    .noRippleClickable { isDatePickerDialogShow = true }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_date_memory_card),
                    contentDescription = "date"
                )
                Text(
                    text = dateTime?.let { "${it.year}.${it.monthValue}.${it.dayOfMonth}" } ?: "",
                    color = BL,
                    fontSize = 20.sp,
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 20.dp)
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