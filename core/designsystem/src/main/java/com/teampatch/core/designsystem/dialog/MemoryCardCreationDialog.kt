package com.teampatch.core.designsystem.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.DefaultTextField
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryCardCreationDialog(
    onDismissRequest: () -> Unit,
    onCompleteRequest: (String, LocalDateTime) -> Unit,
    imageRequest: () -> Unit,
    painter: Painter?
) {
    val datePickerState = rememberDatePickerState()
    var memoryTextValue by rememberSaveable { mutableStateOf("") }
    var dateTime: LocalDateTime? by rememberSaveable { mutableStateOf(null) }
    var isDatePickerDialogShow by rememberSaveable { mutableStateOf(false) }
    val buttonEnable by remember(dateTime, memoryTextValue) {
        derivedStateOf { dateTime != null && memoryTextValue.isNotBlank() }
    }

    if (isDatePickerDialogShow) {
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerDialogShow = false
            },
            confirmButton = {
                Text(
                    text = stringResource(R.string.date_picker_ok),
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 12.dp, end = 12.dp)
                        .nonReplyClickable {
                            val dateMillis =
                                datePickerState.selectedDateMillis ?: return@nonReplyClickable
                            val instant = Instant.ofEpochMilli(dateMillis)
                            dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

                            isDatePickerDialogShow = false
                        }
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.date_picker_cancel),
                    modifier = Modifier
                        .nonReplyClickable {
                            isDatePickerDialogShow = false
                        }
                )
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Dialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(top = 24.dp, bottom = 14.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.memory_card_creation_dialog_title),
                fontFamily = PretendardFontFamily,
                color = BL,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(320.dp)
                    .height(194.dp)
                    .padding(top = 24.dp)
                    .background(G1, RoundedCornerShape(10.dp))
                    .nonReplyClickable { imageRequest() }
            ) {
                if (painter == null) {
                    Image(
                        painter = painterResource(R.drawable.ic_camera_memory_card),
                        contentDescription = "camera"
                    )
                    Canvas(Modifier.fillMaxSize()) {
                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        drawRoundRect(
                            color = G2,
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = pathEffect,
                                cap = StrokeCap.Round
                            ),
                            cornerRadius = CornerRadius(x = 10.dp.toPx())
                        )
                        BorderStroke(width = 2.dp, color = G2)
                    }
                } else {
                    Image(
                        painter = painter,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(
                text = stringResource(R.string.memory_card_creation_dialog_question_memory),
                fontFamily = PretendardFontFamily,
                color = BL,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 24.dp)
            )
            DefaultTextField(
                value = memoryTextValue,
                onValueChange = { memoryTextValue = it },
                hint = { Text(text = stringResource(R.string.memory_card_creation_dialog_question_memory_hint)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.memory_card_creation_dialog_question_date),
                fontFamily = PretendardFontFamily,
                color = BL,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .background(color = WH, shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = G2, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp)
                    .nonReplyClickable { isDatePickerDialogShow = true }
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .heightIn(min = 68.dp)
                    .background(
                        color = if (buttonEnable) MainGreen else G2,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .nonReplyClickable {
                        if (buttonEnable) {
                            onCompleteRequest(memoryTextValue, dateTime!!)
                        }
                    }
            ) {
                Text(
                    text = stringResource(R.string.memory_card_creation_dialog_button),
                    fontFamily = PretendardFontFamily,
                    color = WH,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_close_memory_card),
                contentDescription = "date",
                modifier = Modifier.nonReplyClickable {
                    onDismissRequest()
                }
            )
        }
    }
}

@Preview
@Composable
private fun MemoryCardCreationDialogPreview() {
    HarmonyTheme {
        MemoryCardCreationDialog(
            onDismissRequest = {},
            onCompleteRequest = { _, _ -> },
            imageRequest = {},
            painter = null
        )
    }
}

@Preview
@Composable
private fun MemoryCardCreationDialogPreview1() {
    HarmonyTheme {
        MemoryCardCreationDialog(
            onDismissRequest = {},
            onCompleteRequest = { _, _ -> },
            imageRequest = {},
            painter = painterResource(R.drawable.img_test_memory_card)
        )
    }
}