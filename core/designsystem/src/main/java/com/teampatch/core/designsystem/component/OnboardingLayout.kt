package com.teampatch.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.theme.PretendardFontFamily

/**
 * OnboardingLayout Screen과 다르게 ui를 재사용하기 위해 만든 것이라고 보면됨.
 * 여기에 둘 예정
 */

@Composable
fun OnBoardingLayout(
    title: AnnotatedString,
    subtext: String,
    onBackRequest: () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null, // ✅ bottomBar를 선택적으로 추가
    image: (@Composable () -> Unit)? = null, // ✅ 선택적 image 추가
    imagePadding: Dp = 0.dp, // ✅ 화면에서 조정 가능한 padding 추가
    content: @Composable () -> Unit, // content 인자를 받음
) {
    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                title = {},
                actions = {}
            )
        },
        bottomBar = {
            Column {
                if (image != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = imagePadding), // ✅ bottomBar 위에 간격 추가
                        contentAlignment = Alignment.Center
                    ) {
                        image()
                    }
                }
                bottomBar?.invoke() // ✅ bottomBar는 항상 맨 아래 위치
            }
        }
    ) { scaffoldPaddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = title,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = subtext,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 34.dp)
                ) {
                    content()
                }
            }
        }
    }
}