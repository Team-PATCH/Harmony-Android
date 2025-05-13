package com.teampatch.feature.daily.edit

import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.teampatch.core.domain.model.Todo
import com.teampatch.feature.daily.edit.R.string.btn_complete_daily
import com.teampatch.feature.daily.edit.R.string.select_time
import com.teampatch.feature.daily.edit.R.string.select_week_days
import com.teampatch.feature.daily.edit.R.string.text_per_daily
import com.teampatch.feature.daily.edit.R.string.title_daily
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@Composable
internal fun DailyEditRoute(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (Todo) -> Unit,
    viewModel: DailyEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.dailyEditUiState

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is DailyEditEvent.AddDailyError -> {
                    Toast.makeText(context, "서버 전송 오류", Toast.LENGTH_LONG).show()
                }
                is DailyEditEvent.LoadError -> {
                    Toast.makeText(context, "데이터 불러오기 실패", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    if (!uiState.isLoading) {
        DailyEditScreen(
            onDismissRequest = onDismissRequest,
            onCompleteRequest = onCompleteRequest,
            selectedDays = uiState.selectedDays,
            onDaySelected = { viewModel.toggleSelectedDay(it) },
            selectedTime = uiState.selectedTime,
            onTimeSelected = { viewModel.changeSelectedTime(it) }
        )
    }
}

@Composable
internal fun DailyEditScreen(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (Todo) -> Unit,
    selectedDays: Set<DayOfWeek>,
    onDaySelected: (DayOfWeek) -> Unit,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val textState = rememberSaveable { mutableStateOf("") }
    val daysOfWeek = remember { DayOfWeek.values() }

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
                onClick = {
                    val todo = Todo(
                        id = UUID.randomUUID().toString(),
                        title = textState.value,
                        dateTime = selectedTime?.let { LocalDateTime.of(LocalDate.now(), it) }
                            ?: LocalDateTime.now(),
                        isFinished = false
                    )
                    Log.d("DEBUG", "1. DailyEditScreen: onCompleteRequest todo = $todo")
                    onCompleteRequest(todo)
                },
                enabled = textState.value.isNotBlank(),
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
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Text(stringResource(text_per_daily), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ placeholder 적용
            DefaultTextField(
                value = textState.value,
                onValueChange = {
                    if (it.length <= 200) textState.value = it
                },
                hint = { Text("예) 아침식사 먹기") },
                singleLine = false,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(select_week_days), fontSize = 18.sp)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                daysOfWeek.forEach { day ->
                    FilterChip(
                        selected = selectedDays.contains(day),
                        onClick = { onDaySelected(day) },
                        label = { Text(day.getDisplayName(TextStyle.SHORT, Locale.KOREAN)) },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(select_time), fontSize = 18.sp)

            val calendar = remember { Calendar.getInstance() }
            val hour = remember { calendar.get(Calendar.HOUR_OF_DAY) }
            val minute = remember { calendar.get(Calendar.MINUTE) }

            val timePickerDialog = remember {
                TimePickerDialog(
                    context,
                    { _, selectedHour, selectedMinute ->
                        onTimeSelected(LocalTime.of(selectedHour, selectedMinute))
                    },
                    hour,
                    minute,
                    true
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(WH, RoundedCornerShape(10.dp))
                    .border(1.dp, G2, RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp)
                    .noRippleClickable { timePickerDialog.show() }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_date_memory_card),
                    contentDescription = "time"
                )
                Text(
                    text = selectedTime?.let { "%02d:%02d".format(it.hour, it.minute) }
                        ?: stringResource(select_time),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyEditScreenPreview() {
    HarmonyTheme {
        var selectedDays by remember { mutableStateOf(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)) }
        var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

        DailyEditScreen(
            onDismissRequest = {},
            onCompleteRequest = {},
            selectedDays = selectedDays,
            onDaySelected = { day ->
                selectedDays = selectedDays.toMutableSet().apply {
                    if (contains(day)) remove(day) else add(day)
                }
            },
            selectedTime = selectedTime,
            onTimeSelected = { time -> selectedTime = time }
        )
    }
}