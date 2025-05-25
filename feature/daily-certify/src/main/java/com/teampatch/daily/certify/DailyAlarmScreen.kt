package com.teampatch.daily.certify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.DefaultButtonColor
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.component.TypeWriterText
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
internal fun DailyAlarmRoute(
    fromNotification: Boolean,
    onPickImageScreenRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val viewModel: DailyCertifyViewModel = hiltViewModel()
    val uiState by viewModel.uiState

    // isLoading 추가해서 작업 필요
    DailyAlarmScreen(
        onPickImageScreenRequest = onPickImageScreenRequest,
        onDismissRequest = onDismissRequest,
        uiState = uiState
    )

    // ✅ event 처리
//    LaunchedEffect(viewModel.sideEffect) {
//        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
//            when (it) {
//                DailyCertifySideEffect.LoadError ->
//                    Toast.makeText(context, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
//                DailyCertifySideEffect.PushPermissionDenied ->
//                    Toast.makeText(context, "푸시 권한을 허용해 주세요.", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}

@Composable
internal fun DailyAlarmScreen(
    onPickImageScreenRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    uiState: DailyCertifyUiState,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = {
                    Text(
                        text = "일과 알림",
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 240.dp)
                    )
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                // ✅ ✅ MemoryCard와 동일하게 DefaultButton 사용
                DefaultButton(
                    onClick = onPickImageScreenRequest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "인증사진 남기러 가기")
                }

                Spacer(modifier = Modifier.height(8.dp))

                DefaultButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth(),
                    color = DefaultButtonColor(BL)
                ) {
                    Text(text = "나중에 남기기")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SpeechBubble {
                TypeWriterText(
                    text = buildString {
                        uiState.missionTime?.let {
                            append(it.format(DateTimeFormatter.ofPattern("a h:mm")))
                            append("\n")
                        }
                        append(uiState.missionText)
                    }
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_harmony_talk),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(top = 24.dp)
                    .size(120.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyAlarmScreenPreview() {
    HarmonyTheme {
        DailyAlarmScreen(
            onPickImageScreenRequest = { },
            onDismissRequest = { },
            uiState = DailyCertifyUiState(
                missionText = "공원 산책 가서 비둘기 사진 찍기",
                missionTime = LocalTime.of(14, 30)
            )
        )
    }
}