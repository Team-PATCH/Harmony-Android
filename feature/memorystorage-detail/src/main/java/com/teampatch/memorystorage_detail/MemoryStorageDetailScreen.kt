package com.teampatch.memorystorage_detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.component.AppBar
import com.teampatch.core.designsystem.component.DefaultButton
import com.teampatch.core.designsystem.component.DefaultTextField
import com.teampatch.core.designsystem.component.MemoryInfoView
import com.teampatch.core.designsystem.component.OnboardingAppBar
import com.teampatch.core.designsystem.component.SpeechBubble
import com.teampatch.core.designsystem.component.TypeWriterText
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
fun MemoryStorageDetailScreen(
    onBackRequest: () -> Unit,
    uiState: MemoryStorageDetailUiState,
    onCompleteRequest: () -> Unit

    ) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            OnboardingAppBar {
                IconButton(onClick = onBackRequest) {
                }
            }
        },
        bottomBar = {
            DefaultButton(
                onClick = { onCompleteRequest() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text(text = "대화 전체 보기")
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
                        painter = painterResource(R.drawable.ic_camera_memory),
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

            Spacer(modifier = Modifier
                .padding(top = 10.dp))

            MemoryInfoView(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "민준갓",
                description = "소리쳐"
            )

            Spacer(modifier = Modifier
                .padding(top = 10.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 36.dp, bottom = 32.dp)
            ) {
                DefaultTextField(
                    value = "",
                    onValueChange = {

                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier
                .padding(top = 10.dp)
            )


        }
    }
}


@Preview
@Composable
private fun MemoryStorageDetailScreenPreview() {
    HarmonyTheme {
        MemoryStorageDetailScreen(
            onBackRequest = {},
            uiState = MemoryStorageDetailUiState(),
            onCompleteRequest = {}
            )
    }
}