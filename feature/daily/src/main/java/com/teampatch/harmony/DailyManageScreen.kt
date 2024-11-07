package com.teampatch.harmony

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R.drawable.ic_edit
import com.teampatch.core.designsystem.component.BackButtonAppBar
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
fun DailyManageRoute(
    
) {
    val context = LocalContext.current
    // TODO: state - viewmodel
}

@Composable
fun DailyManageScreen(
    // TODO: Route
    onBackRequest: () -> Unit,
    onEditClick: () -> Unit,
) {
    // TODO:  ??
    Scaffold(
        topBar = {
            BackButtonAppBar(
                onBackRequest = onBackRequest,
                title = {
                    Text(
                        text = "2024년 6월 13일 일과",
                        fontSize = 22.sp,
                        fontFamily = PretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = BL
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(ic_edit),
                        contentDescription = "edit",
                        modifier = Modifier
                            .padding(end = 21.dp)
                            .nonReplyClickable {
                                onEditClick()
                            }
                    )
                }
            )
        },
    ) { scaffoldPaddingValues ->

    }
}

@Preview
@Composable
private fun DailyManageScreenPreview() {
    HarmonyTheme {
        DailyManageScreen()
    }
}