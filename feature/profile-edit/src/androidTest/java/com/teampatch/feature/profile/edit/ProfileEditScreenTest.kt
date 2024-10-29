package com.teampatch.feature.profile.edit

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileEditScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var editProfileUseCase: EditProfileUseCase
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase
    private lateinit var fakeViewModel: ProfileEditViewModel

    @Before
    fun init() {
        initEditProfileUseCase()
        initGetUserInfoUseCase()
        initViewModel()
        initView()
    }

    private fun initEditProfileUseCase() {
        editProfileUseCase = object : EditProfileUseCase {
            override suspend fun invoke(relation: String, name: String, profileImage: Image?) {
                Log.d(
                    TAG,
                    "EditProfileUseCase " +
                            "relation=$relation, name=$name, profileImage=$profileImage"
                )
            }
        }
    }

    private fun initGetUserInfoUseCase() {
        getUserInfoUseCase = object : GetUserInfoUseCase {
            override fun invoke(): Flow<User> {
                return flowOf(
                    User(
                        uid = "uid",
                        name = "android",
                        relation = "father",
                        profileImageUrl = null,
                        role = Role.MEMBER
                    )
                )
            }
        }
    }

    private fun initViewModel() {
        fakeViewModel = ProfileEditViewModel(
            editProfileUseCase = editProfileUseCase,
            getUserInfoUseCase = getUserInfoUseCase,
        )
    }

    private fun initView() {
        composeRule.setContent {
            ProfileEditRoute(
                onCompleteRequest = {
                    Log.d(TAG, "onCompleteRequest")
                },
                profileEditViewModel = fakeViewModel
            )
        }
    }

    @Test
    fun `초기_데이터_로드_테스트`() {
        composeRule.onNodeWithText("father").assertIsDisplayed()
        composeRule.onNodeWithText("android").assertIsDisplayed()
    }

    @Test
    fun `이름_텍스트_필드_테스트`() {
        composeRule.onNodeWithTag("name_text_field").apply {
            performTextClearance()
            performTextInput("민준")
        }
        composeRule.onNodeWithText("수정하기").performClick()
        val name = fakeViewModel.profileEditUiState.value.name
        assert(name == "민준") {
            name
        }
    }

    @Test
    fun `관계_텍스트_필드_테스트`() {
        composeRule.onNodeWithTag("relation_text_field").apply {
            performTextClearance()
            performTextInput("아빠")
        }

        composeRule.onNodeWithText("수정하기").performClick()
        val relation = fakeViewModel.profileEditUiState.value.relation
        assert(relation == "아빠") {
            relation
        }
    }

    @Test
    fun `버튼_비활성화_테스트`() {
        composeRule.onNodeWithTag("name_text_field").performTextInput("name")
        composeRule.onNodeWithTag("relation_text_field").performTextClearance()
        composeRule.onNodeWithText("수정하기").assertIsNotEnabled()

        composeRule.onNodeWithTag("relation_text_field").performTextInput("relation")
        composeRule.onNodeWithTag("name_text_field").performTextClearance()
        composeRule.onNodeWithText("수정하기").assertIsNotEnabled()

        composeRule.onNodeWithTag("name_text_field").performTextInput("name")
        composeRule.onNodeWithTag("relation_text_field").performTextInput("relation")
        composeRule.onNodeWithText("수정하기").assertIsEnabled()
    }

    companion object {
        private const val TAG = "ProfileEditScreenTest"
    }
}