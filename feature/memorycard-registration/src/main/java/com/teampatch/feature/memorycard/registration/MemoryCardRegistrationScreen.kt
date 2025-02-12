package com.teampatch.feature.memorycard.registration

import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.LifecycleEventEffect
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.common.checkRadioAudioPermission
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
import com.teampatch.feature.memorycard.registration.utils.AudioRecorderHelper

@Composable
internal fun MemoryCardRegistrationRoute(
    onDismissRequest: () -> Unit,
    onMemoryStorePageRequest: () -> Unit,
    viewModel: MemoryCardRegistrationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState

    if (!uiState.isLoading) {
        MemoryCardRegistrationScreen(
            onDismissRequest = onDismissRequest,
            onRecordComplete = {
                viewModel.uploadMemoryCardAudioRecordFile(it)
                onMemoryStorePageRequest()
            },
            uiState = uiState
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                MemoryCardRegistrationSideEffect.LoadError -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.toast_data_load_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
internal fun MemoryCardRegistrationScreen(
    onDismissRequest: () -> Unit,
    onRecordComplete: (Uri) -> Unit,
    uiState: MemoryCardRegistrationUiState,
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val audioRecorderHelper = remember { AudioRecorderHelper(context) }
    var recordState: RecordState by remember { mutableStateOf(RecordState.INIT) }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        if (recordState != RecordState.RECORDING) return@LifecycleEventEffect
        audioRecorderHelper.stop()
        audioRecorderHelper.release()
        recordState = RecordState.INIT
    }

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
                    if (!context.checkRadioAudioPermission()) {
                        activity?.requestRadioAudioPermission()
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_audio_permission_request),
                            Toast.LENGTH_LONG
                        ).show()
                        return@DefaultButton
                    }

                    try {
                        when (recordState) {
                            RecordState.INIT -> {
                                audioRecorderHelper.prepare()
                                audioRecorderHelper.start()
                                recordState = RecordState.RECORDING
                            }

                            RecordState.RECORDING -> {
                                audioRecorderHelper.stop()
                                audioRecorderHelper.release()
                                onRecordComplete(audioRecorderHelper.getResultRecordFile())
                                recordState = RecordState.COMPLETE
                            }

                            RecordState.COMPLETE -> {}
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_audio_recording_error),
                            Toast.LENGTH_LONG
                        ).show()
                        onDismissRequest()
                    }
                },
                color = DefaultButtonColor(
                    containerColor = when (recordState) {
                        RecordState.RECORDING -> BL
                        else -> MainGreen
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text(
                    text = when (recordState) {
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
                        contentDescription = "camera"
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = uiState.imageUrl
                        ),
                        contentDescription = "image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
            SpeechBubble {
                TypeWriterText(
                    text = when (recordState) {
                        RecordState.INIT -> {
                            stringResource(R.string.text_speechbuble_init)
                        }

                        RecordState.RECORDING -> {
                            uiState.questions.getOrNull(uiState.questionProgressIndex) ?: ""
                        }

                        RecordState.COMPLETE -> {
                            stringResource(R.string.text_speechbuble_complete)
                        }
                    }
                )
            }
            Image(
                painter = painterResource(ic_harmony_talk),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if (recordState == RecordState.RECORDING) {
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

@Preview
@Composable
private fun MemoryCardRegistrationScreenPreview() {
    HarmonyTheme {
        MemoryCardRegistrationScreen(
            onDismissRequest = {},
            onRecordComplete = {},
            uiState = MemoryCardRegistrationUiState()
        )
    }
}