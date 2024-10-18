package com.teampatch.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R.drawable.ic_check_todo
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G2
import com.teampatch.core.designsystem.theme.G4
import com.teampatch.core.designsystem.theme.Green2
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
fun DailyRoutineCard(
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    dateTime: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .border(1.dp, G2, RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(1.dp, if (checked) MainGreen else G2, RoundedCornerShape(10.dp))
                .background(if (checked) Green2 else G1, RoundedCornerShape(10.dp))
                .size(64.dp)
                .nonReplyClickable { onCheckedChange(!checked) },
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(ic_check_todo),
                    contentDescription = "check",
                    tint = MainGreen
                )
            }
        }
        Column(
            modifier = Modifier.padding(start = 26.dp)
        ) {
            Text(
                text = dateTime,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
                color = G4,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = text,
                fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                color = BL,
                softWrap = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyRoutineCardPreview() {
    HarmonyTheme {
        DailyRoutineCard(
            onCheckedChange = {},
            checked = true,
            dateTime = "오전 11시",
            text = "공원 산책 가서 비둘기 사진 찍기",
        )
    }
}