@file:OptIn(ExperimentalMaterial3Api::class)

package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.R

@Composable
fun OnBoardingScreen(
    title: String,
    content: @Composable () -> Unit,  // content 인자를 받음
    onBackClick: () -> Unit,
    onNextClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // 뒤로 가기 버튼
        IconButton(onClick = { /* TODO: 뒤로 가기 액션 */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 메인 텍스트
        Image(
            painter = painterResource(id = R.drawable.text_top1_onboarding),
            contentDescription = "text top1 onboarding",
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 서브 텍스트
        Image(
            painter = painterResource(id = R.drawable.text_sub1_onboarding),
            contentDescription = "text sub1 onboarding",
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(45.3.dp))

        // 버튼: 가족 공간 만들기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { /* TODO: 가족 공간 만들기 액션 */ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_make_space_onboarding),
                contentDescription = "가족 공간 만들기 이미지",
                modifier = Modifier.fillMaxSize(), // 이미지가 버튼 영역을 채우도록 설정
                contentScale = ContentScale.Crop // 이미지가 버튼에 맞게 잘리거나 확장됨
            )
        }

        Spacer(modifier = Modifier.height(18.6.dp))

        // 버튼: 가족 공간 입장하기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { /* TODO: 가족 공간 만들기 액션 */ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_enter_space_onboarding),
                contentDescription = "가족 공간 들어가기 이미지",
                modifier = Modifier.fillMaxSize(), // 이미지가 버튼 영역을 채우도록 설정
                contentScale = ContentScale.Crop // 이미지가 버튼에 맞게 잘리거나 확장됨
            )
        }
    }
}

@Composable
fun Screen4_1(
    selectedMember: String,
    onMemberSelected: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    onNextClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val memberOptions = listOf("할머니", "할아버지")
    val isFormValid = selectedMember.isNotEmpty() && lastName.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("누구를 위해 만드시나요?") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "할머니나 할아버지의 성함을 입력해 주세요.",
                style = MaterialTheme.typography.body1,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Dropdown for selecting a family member
            Row {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (selectedMember.isEmpty()) "선택" else selectedMember)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        memberOptions.forEach { member ->
                            DropdownMenuItem(onClick = {
                                onMemberSelected(member)
                                expanded = false
                            }) {
                                Text(text = member)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Input for last name
                OutlinedTextField(
                    value = lastName,
                    onValueChange = onLastNameChanged,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    placeholder = { Text("성함") },
                    enabled = selectedMember.isNotEmpty()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next button
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isFormValid) Color.Green else Color.LightGray
                )
            ) {
                Text(text = "다음")
            }
        }
    }
}

// OnBoardingCommonScreen Preview
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        title = "Preview Title",
        onBackClick = {},
        onNextClick = {},
        content = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Screen4_1Preview() {
    Screen4_1(
        title = "Preview Title",
        onBackClick = {},
        onNextClick = {},
        content = {}
    )
}
