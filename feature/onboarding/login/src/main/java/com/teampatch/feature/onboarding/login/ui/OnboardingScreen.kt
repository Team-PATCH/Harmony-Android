@file:OptIn(ExperimentalMaterial3Api::class)

package com.teampatch.feature.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.R.drawable.btn_add_profile
import com.teampatch.core.designsystem.R.drawable.btn_back
import com.teampatch.core.designsystem.R.drawable.btn_enter_space_onboarding
import com.teampatch.core.designsystem.R.drawable.btn_make_space_onboarding
import com.teampatch.core.designsystem.R.drawable.btn_share_code_invitation
import com.teampatch.core.designsystem.R.drawable.ic_my_appbar
import com.teampatch.core.designsystem.R.drawable.img_guide_start
import com.teampatch.core.designsystem.R.drawable.img_logo_in_login
import com.teampatch.core.designsystem.component.AdditionMemoryCard
import com.teampatch.core.designsystem.component.CollapseMemoryCard
import com.teampatch.core.designsystem.component.ExpandMemoryCard
import com.teampatch.core.designsystem.component.HomeAppBar
import com.teampatch.core.designsystem.component.OnboardingAppBar
import com.teampatch.core.designsystem.component.nonReplyClickable
import com.teampatch.core.designsystem.theme.BL
import com.teampatch.core.designsystem.theme.G1
import com.teampatch.core.designsystem.theme.G5
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.core.designsystem.theme.MainGreen
import com.teampatch.core.designsystem.theme.PretendardFontFamily
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_enter_code
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_enter_grandparents_name
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_enter_my_name
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_invite_grandparents
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_make_space
import com.teampatch.feature.onboarding.login.R.string.subtext_onboarding_setting_prfile_image

@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Scaffold의 기본 패딩
                .padding(top = 135.dp, bottom = 8.dp) // Column 시작 위치에 추가 패딩
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                verticalArrangement = Arrangement.Top,
                verticalArrangement = Arrangement.SpaceBetween, // 첫 요소는 위, 마지막 요소는 아래에 붙음
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = img_logo_in_login),
                    contentDescription = "Logo Harmony",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(465.dp))

                Button(
                    onClick = { /* Handle Kakao Login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(68.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE812)), // 배경색은 이미지를 꽉 채우면 안 보이게 됩니다.
                    shape = RoundedCornerShape(10.dp)
                ) {
                    // 이미지 리소스를 painterResource로 불러오고 버튼을 꽉 채움
                    Image(
                        painter = painterResource(id = R.drawable.kakao_login_medium_wide), // 카카오 로그인 이미지
                        contentDescription = "Kakao Login",
                        modifier = Modifier.fillMaxSize(), // 버튼 크기를 꽉 채움
//                    contentScale = ContentScale.Crop // 이미지가 버튼에 맞게 잘리거나 확장됨
//                    modifier = Modifier.fillMaxHeight(), // 버튼 높이에 맞게 이미지 채우기
                        contentScale = ContentScale.Fit // 이미지가 잘리지 않고 버튼 안에 맞춰짐
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionNotificationScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 92.dp, bottom = 8.dp) // Column 시작 위치에 추가 패딩
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.SpaceBetween, // 첫 요소는 위, 마지막 요소는 아래에 붙음
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.teampatch.core.designsystem.R.drawable.image_permission_to_notify),
                    contentDescription = "Permission Notification Image",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Image(
                    painter = painterResource(id = com.teampatch.core.designsystem.R.drawable.img_character_fullbody_mony),
                    contentDescription = "Full-Body Mony Character",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = { /* Handle Start Process */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(68.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 투명한 배경
                    contentPadding = PaddingValues(0.dp), // 버튼의 기본 내부 패딩 제거
                    shape = RoundedCornerShape(10.dp)
                ) {
                    // 이미지 리소스를 painterResource로 불러오고 버튼을 꽉 채움
                    Image(
                        painter = painterResource(id = R.drawable.btn_start_harmony), // 카카오 로그인 이미지
                        contentDescription = "Harmoy Start Process",
                        modifier = Modifier.fillMaxSize(), // 이미지가 버튼의 크기를 꽉 채움
                        contentScale = ContentScale.Crop // 이미지가 버튼 크기에 맞춰 잘림
                    )
                }
            }
        }
    }
}


@Composable
fun OnBoardingLayout(
    onBackRequest: () -> Unit,
    title: AnnotatedString,
    subtext: String,
    content: @Composable () -> Unit // content 인자를 받음
) {
    Scaffold(
        topBar = {
            OnboardingAppBar {
                IconButton(onClick = onBackRequest) {
                }
            }
        }
    ) { scaffoldPaddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPaddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Title, Subtitle, and Spacer sections
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
                }
            }

            // Content Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .background(G1)
                ) {
                    content() // Passing the composable content
                }
            }
        }
    }
}


@Composable
fun ChooseSpace() {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = BL)) {
                append("먼저 ")
            }
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("가족공간")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("을\n만들어 주세요.")
            }
        },
        subtext = stringResource(subtext_onboarding_make_space),
        onBackRequest = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // 배경색 설정
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(btn_make_space_onboarding),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

            // Spacer 대신 Box로 배경색을 설정한 여백 추가
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(Color.White) // 여백의 배경색을 설정
            )

            Image(
                painter = painterResource(btn_enter_space_onboarding),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun InputVipName() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("할머니") }

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("누구")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("를 위해\n")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("만드시나요")
            }
        },
        subtext = stringResource(subtext_onboarding_enter_grandparents_name),
        onBackRequest = {
            // Handle back click here
        }
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedItem, modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,  // 항상 ArrowDropDown 아이콘만 표시
                        contentDescription = "Toggle Dropdown"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("할머니") },
                        onClick = {
                            selectedItem = "할머니"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("할아버지") },
                        onClick = {
                            selectedItem = "할아버지"
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InputMemberName() {
    // 상태 변수로 관계와 이름을 저장
    var relation by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    // '다음' 버튼 활성화 상태
    val isNextEnabled = relation.isNotEmpty() && name.isNotEmpty()

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = BL)) {
                append("할머니와\n")
            }
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("어떤 관계")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("인가요?")
            }
        },
        subtext = stringResource(subtext_onboarding_enter_my_name),
        onBackRequest = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // 관계 입력 필드
            OutlinedTextField(
                value = relation,
                onValueChange = { relation = it },
                label = { Text("관계 (예: 손녀)") },
                placeholder = { Text("예) 손녀") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 이름 입력 필드
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") },
                placeholder = { Text("이름을 입력해 주세요.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // '다음' 버튼
            Button(
                onClick = { /* 다음 단계로 이동 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),  // 버튼의 높이 설정
                enabled = isNextEnabled,  // 활성화 여부
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isNextEnabled) Color(0xFF4CAF50) else Color(0xFFD3D3D3), // 초록색 또는 회색
                    contentColor = Color.White
                )
            ) {
                Text(text = "다음")
            }
        }
    }
}



@Composable
fun InputProfileSettings() {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = BL)) {
                append("마지막으로\n")
            }
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("프로필 사진")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("을 설정해요.")
            }
        },
        subtext = stringResource(subtext_onboarding_setting_prfile_image),
        onBackRequest = {  }
    ) {
        Image(
            painter = painterResource(btn_add_profile), null,
            contentScale = ContentScale.Crop, // 이미지가 잘리지 않고 버튼 안에 맞춰짐
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun InviteVip() {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("윤여정 할머니")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("를\n")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("초대 해주세요.")
            }
        },
        subtext = stringResource(subtext_onboarding_invite_grandparents),
        onBackRequest = { /*TODO*/ }
    ) {
        Image(painter = painterResource(btn_share_code_invitation), null)
    }
}

@Composable
fun InsertInvitaionCode() {
    // 상태 변수로 초대 코드의 각 자리를 저장
    var code by remember { mutableStateOf("") }

    // 버튼 활성화 여부 (5자리 모두 입력되면 활성화)
    val isNextEnabled = code.length == 5

    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("초대코드")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("를\n")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("만들어 주세요.")
            }
        },
        subtext = stringResource(subtext_onboarding_enter_code),
        onBackRequest = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // 초대 코드 입력 필드
            OutlinedTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 5) {
                        code = it
                    }
                },
                label = { Text("초대코드") },
                placeholder = { Text("12345") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation()  // 코드 숨김 처리
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 다음 버튼
            Button(
                onClick = { /* 초대 코드 확인 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),  // 버튼의 높이 설정
                enabled = isNextEnabled,  // 활성화 여부: 5자리가 아니면 비활성화
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isNextEnabled) Color(0xFF4CAF50) else Color(0xFFD3D3D3),  // 활성화시 초록색, 비활성화시 회색
                    contentColor = Color.White
                )
            ) {
                Text(text = "다음")
            }
        }
    }
}

@Composable
fun EnterSpaceInSingularState() {
    OnBoardingLayout(
        title = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MainGreen)) {
                append("손녀 조다은님")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("이\n")
            }
            withStyle(style = SpanStyle(color = BL)) {
                append("만든 가족 공간이에요.")
            }
        },
        subtext = "",
        onBackRequest = { /*TODO*/ }
    ) {
        Image(painter = painterResource(ic_my_appbar), null)

        Spacer(modifier = Modifier.height(221.dp))

        Image(painter = painterResource(img_guide_start), null)

        Spacer(modifier = Modifier.height(34.dp))

        // 다음 버튼
        Button(
            onClick = { /* 초대 코드 확인 처리 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),  // 버튼의 높이 설정
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),  // 활성화시 초록색, 비활성화시 회색
                contentColor = Color.White
            )
        ) {
            Text(text = "다음")
        }
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


//@Preview(showBackground = true)
//@Composable
//fun OnBoardingScreenPreview() {
//    OnBoardingLayout(
//        title = "할배요",
//        subtext = "할매요",
//        onBackClick = {},
//        content = {}
//    )
//}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@Preview(showBackground = true)
@Composable
fun PermissionNotificationScreenPreview() {
    PermissionNotificationScreen()
}

@Preview(showBackground = true)
@Composable
private fun ChooseSpacePreview() {
    HarmonyTheme {
        ChooseSpace()
    }
}

@Preview(showBackground = true) //
//@Preview(showSystemUi = true) // 원래 배경색이 없는데
@Composable
private fun InputVipNamePreview() {
    HarmonyTheme {
        InputVipName()
    }
}

@Preview(showBackground = true)
@Composable
private fun InputMemberNamePreview() {
    HarmonyTheme {
        InputMemberName()
    }
}


@Preview(showBackground = true)
@Composable
private fun InputProfileSettingsPreview() {
    HarmonyTheme {
        InputProfileSettings()
//        Text(
//            text = buildAnnotatedString {
//                withStyle(style = SpanStyle(color = BL)) {
//                    append("할머니와\n")
//                }
//
//                withStyle(style = SpanStyle(color = MainGreen)) {
//                    append("어떤 관계")
//                }
//                withStyle(SpanStyle(color = BL)) {
//                    append("인가요?")
//                }
//            },
//            fontFamily = PretendardFontFamily,
//            fontWeight = FontWeight.W700,
//            fontSize = 28.sp,
//        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InviteVipPreview() {
    HarmonyTheme {
        InviteVip()
    }
}

@Preview(showBackground = true)
@Composable
private fun InsertInvitaionCodePreview() {
    HarmonyTheme {
        InsertInvitaionCode()
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterSpaceInSingularStatePreview() {
    HarmonyTheme {
        EnterSpaceInSingularState()
    }
}