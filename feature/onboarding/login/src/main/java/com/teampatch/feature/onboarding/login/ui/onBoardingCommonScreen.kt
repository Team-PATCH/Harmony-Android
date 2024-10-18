@file:OptIn(ExperimentalMaterial3Api::class)

package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingCommonScreen(
    title: String,
    content: @Composable () -> Unit,  // content 인자를 받음
    onBackClick: () -> Unit,
    onNextClick: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 전달된 content를 화면에 출력
                content()

                // '다음' 버튼은 onNextClick이 있으면 보여주기
                if (onNextClick != null) {
                    Button(
                        onClick = onNextClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "다음")
                    }
                }
            }
        }
    )
}

@Composable
fun OnBoardingScreen1(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    OnBoardingCommonScreen(
        title = "먼저 가족 공간을 만들어 주세요.", // 해당 화면의 타이틀
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        content = {  // 이 부분에서 content 인자를 전달함
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /* 가족 공간 만들기 로직 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "가족 공간 만들기")
                }
                Button(
                    onClick = { /* 가족 공간 참여하기 로직 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "가족 공간 참여하기")
                }
            }
        }
    )
}

// OnBoardingCommonScreen Preview
@Preview(showBackground = true)
@Composable
fun OnBoardingCommonScreenPreview() {
    OnBoardingCommonScreen(
        title = "Preview Title",
        onBackClick = {},
        onNextClick = {},
        content = {}
    )
}

// OnBoardingScreen1 Preview
@Preview(showBackground = true)
@Composable
fun OnBoardingScreen1Preview() {
    OnBoardingScreen1(
        onBackClick = {},
        onNextClick = {}
    )
}