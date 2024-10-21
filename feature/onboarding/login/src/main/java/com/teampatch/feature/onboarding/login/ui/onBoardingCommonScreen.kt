@file:OptIn(ExperimentalMaterial3Api::class)

package com.teampatch.feature.onboarding.login.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily

@Composable
fun OnBoardingLayout(
    title: String,
    subtext: String,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit  // content 인자를 받음
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // 뒤로 가기 버튼
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = title,
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 28.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = subtext,
            color = G5,
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(45.3.dp))

        content()
    }
}

@Composable
fun InputVipName() {
    OnBoardingLayout(
        title = "누구를 위해\n" +
                "만드시나요?",
        subtext = "할머니나 할아버지의 성함을\n" +
                "입력해 주세요.",
        onBackClick = {

        }
    ) {

        Column {
            DropdownMenu(
                expanded = true,
                onDismissRequest = {  }
            ) {

                DropdownMenuItem(
                    text = { Text("할머니") },
                    onClick = { Log.d("OnBoarding", "onClick") }
                )

                DropdownMenuItem(
                    text = { Text("할아버지") },
                    onClick = { Log.d("OnBoarding", "onClick") }
                )
            }
        }
    }
}

@Composable
fun InputProfileSettings(modifier: Modifier = Modifier) {
    OnBoardingLayout(
        title = "마지막으로\n" +
                "프로필 사진을 설정해요.",
        subtext = "할머니에게 보여질\n" +
                "프로필 사진을 설정해 주세요.",
        onBackClick = {  }
    ) {

        Image(painter = painterResource(ic_my_appbar), null)
    }
}

@Preview
@Composable
private fun InputProfileSettingsPreview() {
    HarmonyTheme {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = BL)) {
                    append("할머니와\n")
                }

                withStyle(style = SpanStyle(color = MainGreen)) {
                    append("어떤 관계")
                }
                withStyle(SpanStyle(color = BL)) {
                    append("인가요?")
                }
            },
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 28.sp,
        )
    }
}

//@Preview(showBackground = true) //
@Preview(showSystemUi = true) // 원래 배경색이 없는데
@Composable
private fun InputVipNamePreview() {
    HarmonyTheme {
        InputVipName()
    }
}
//@Composable
//fun Screen4_1(
//    selectedMember: String,
//    onMemberSelected: (String) -> Unit,
//    lastName: String,
//    onLastNameChanged: (String) -> Unit,
//    onNextClick: () -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//    val memberOptions = listOf("할머니", "할아버지")
//    val isFormValid = selectedMember.isNotEmpty() && lastName.isNotEmpty()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("누구를 위해 만드시나요?") },
//                navigationIcon = {
//                    IconButton(onClick = { /* Handle back navigation */ }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "할머니나 할아버지의 성함을 입력해 주세요.",
//                style = MaterialTheme.typography.body1,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Dropdown for selecting a family member
//            Row {
//                Box(modifier = Modifier.weight(1f)) {
//                    OutlinedButton(
//                        onClick = { expanded = true },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(text = if (selectedMember.isEmpty()) "선택" else selectedMember)
//                        Icon(
//                            imageVector = Icons.Default.ArrowDropDown,
//                            contentDescription = "Dropdown"
//                        )
//                    }
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        memberOptions.forEach { member ->
//                            DropdownMenuItem(onClick = {
//                                onMemberSelected(member)
//                                expanded = false
//                            }) {
//                                Text(text = member)
//                            }
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                // Input for last name
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = onLastNameChanged,
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth(),
//                    placeholder = { Text("성함") },
//                    enabled = selectedMember.isNotEmpty()
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // Next button
//            Button(
//                onClick = onNextClick,
//                modifier = Modifier.fillMaxWidth(),
//                enabled = isFormValid,
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = if (isFormValid) Color.Green else Color.LightGray
//                )
//            ) {
//                Text(text = "다음")
//            }
//        }
//    }
//}


// OnBoardingCommonScreen Preview
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingLayout(
        title = "할배요",
        subtext = "할매요",
        onBackClick = {},
        content = {}
    )
}

