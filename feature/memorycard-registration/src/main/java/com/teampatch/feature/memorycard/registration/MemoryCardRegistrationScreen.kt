package com.teampatch.feature.memorycard.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.common.findActivity
import com.teampatch.core.common.requestRadioAudioPermission
import com.teampatch.core.designsystem.R.drawable.ic_camera_memory
import com.teampatch.core.designsystem.R.drawable.ic_close_memory_card
import com.teampatch.core.designsystem.R.drawable.ic_harmony_talk
import com.teampatch.core.designsystem.R.drawable.ic_voice_memorycard
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.DefaultButtonColor
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.component.TypeWriterText
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.utils.noRippleClickable
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationSideEffect
import com.teampatch.feature.memorycard.registration.model.MemoryCardRegistrationUiState
import com.teampatch.feature.memorycard.registration.model.RecordState

@Composable
internal fun MemoryCardRegistrationRoute(
    onDismissRequest: () -> Unit,
    onMemoryStorePageRequest: () -> Unit,
    viewModel: MemoryCardRegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val lifecycle = LocalLifecycleOwner.current
    val uiState = viewModel.uiState

    if (!uiState.isLoading) {
        MemoryCardRegistrationScreen(
            onDismissRequest = onDismissRequest,
            onRecordRequest = viewModel::startRecord,
            onRecordStopRequest = viewModel::stopRecord,
            onMemoryStorePageRequest = onMemoryStorePageRequest,
            uiState = uiState
        )
    }

    DisposableEffect(lifecycle.lifecycle.currentState) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    if (viewModel.uiState.recordState == RecordState.RECORDING) {
                        viewModel.resumeRecording()
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    if (viewModel.uiState.recordState == RecordState.RECORDING) {
                        viewModel.pauseRecording()
                    }
                }
                else -> {}
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                MemoryCardRegistrationSideEffect.LoadError ->
                    Toast.makeText(context, "데이터 로드를 실패하였습니다.", Toast.LENGTH_SHORT).show()

                MemoryCardRegistrationSideEffect.RecordingError ->
                    Toast.makeText(context, "음성 녹음 실패하였습니다.", Toast.LENGTH_SHORT).show()

                MemoryCardRegistrationSideEffect.RecordingPermissionDeniedError -> {
                    Toast.makeText(context, "오디오 녹음 권한이 필요합니다.", Toast.LENGTH_LONG).show()
                    activity?.requestRadioAudioPermission()
                }
            }
        }
    }
}

@Composable
internal fun MemoryCardRegistrationScreen(
    onDismissRequest: () -> Unit,
    onRecordRequest: () -> Unit,
    onRecordStopRequest: () -> Unit,
    onMemoryStorePageRequest: () -> Unit,
    uiState: MemoryCardRegistrationUiState
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(
                title = {
                    Text(
                        text = uiState.title,
                        maxLines = 1,
                        modifier = Modifier
                            .widthIn(max = 240.dp)
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(ic_close_memory_card),
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
                    when (uiState.recordState) {
                        RecordState.INIT -> {
                            onRecordRequest()
                        }

                        RecordState.RECORDING -> {
                            onRecordStopRequest()
                        }

                        RecordState.COMPLETE -> {
                            onMemoryStorePageRequest()
                        }
                    }
                },
                color = DefaultButtonColor(
                    containerColor = when (uiState.recordState) {
                        RecordState.RECORDING -> BL
                        else -> MainGreen
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text(
                    text = when (uiState.recordState) {
                        RecordState.INIT -> stringResource(R.string.btn_communication_start)
                        RecordState.RECORDING -> stringResource(R.string.btn_communication_end)
                        RecordState.COMPLETE -> stringResource(R.string.btn_communication_complete)
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(G1)
            ) {
                if (uiState.imageUrl == null) {
                    Image(
                        painter = painterResource(ic_camera_memory),
                        contentDescription = "camera",
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = uiState.imageUrl,
                        ),
                        contentDescription = "image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            SpeechBubble {
                TypeWriterText(text = context.getSpeechBubbleText(uiState))
            }
            Image(
                painter = painterResource(ic_harmony_talk),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if (uiState.recordState == RecordState.RECORDING) {
                Image(
                    painter = painterResource(ic_voice_memorycard),
                    contentDescription = "recording",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

private fun Context.getSpeechBubbleText(uiState: MemoryCardRegistrationUiState): String {
    return when (uiState.recordState) {
        RecordState.INIT -> {
            getString(R.string.text_speechbuble_init)
        }

        RecordState.RECORDING -> {
            uiState.questions.getOrNull(uiState.questionProgressIndex) ?: ""
        }

        RecordState.COMPLETE -> {
            getString(R.string.text_speechbuble_complete)
        }
    }
}

@Preview
@Composable
private fun MemoryCardRegistrationScreenPreview() {
    HarmonyTheme {
        MemoryCardRegistrationScreen(
            onDismissRequest = {},
            onRecordRequest = {},
            onRecordStopRequest = {},
            onMemoryStorePageRequest = {},
            uiState = MemoryCardRegistrationUiState()
        )
    }
}