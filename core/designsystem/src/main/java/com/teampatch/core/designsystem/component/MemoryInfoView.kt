package com.teampatch.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.core.designsystem.theme.WH

/** *
 * 민준갓의 ExpandMemoryCard를 참고하여 만듬(추억저장소 메인에 임의로 보여주기 위함)
 */

@Composable
fun MemoryInfoView(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WH)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally // 텍스트 가운데 정렬
        ) {
            Text(
                text = title,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BL,
            )
            Text(
                text = description,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = G5,
                modifier = Modifier.padding(top = 6.dp)
            )

            // Row로 정렬 및 각 텍스트를 원으로 스타일링
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp), // 각 원 간 간격 설정
                verticalAlignment = Alignment.CenterVertically, // Row 내부 요소 정렬
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 8.dp) // Row 패딩
            ) {
                // 첫 번째 원형 텍스트
                Box(
                    modifier = Modifier
                        .size(40.dp) // 원 크기
                        .background(MainGreen, shape = CircleShape) // 원 배경색과 둥근 모양
                        .padding(1.dp), // 안쪽 여백
                    contentAlignment = Alignment.Center // 텍스트 가운데 정렬
                ) {
                    Text(
                        text = stringResource(R.string.family_profile_manager),
                        color = WH,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                // 두 번째 원형 텍스트
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MainGreen, shape = CircleShape)
                        .padding(1.dp), // 안쪽 여백
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.family_profile_manager),
                        color = WH,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                // 세 번째 원형 텍스트
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MainGreen, shape = CircleShape)
                        .padding(1.dp), // 안쪽 여백
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.family_profile_manager),
                        color = WH,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun MemoryInfoViewPreview() {
    HarmonyTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(G1)
                .padding(top = 28.dp, bottom = 24.dp)
        ) {
            MemoryInfoView(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "다은이 태어난 날",
                description = "1999년 5월 4일",
            )
        }
    }
}
