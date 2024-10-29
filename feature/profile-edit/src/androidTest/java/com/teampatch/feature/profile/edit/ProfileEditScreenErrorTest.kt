package com.teampatch.feature.profile.edit

import android.util.Log
import androidx.compose.ui.test.junit4.createComposeRule
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileEditScreenErrorTest {

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
                throw IllegalStateException()
            }
        }
    }

    private fun initGetUserInfoUseCase() {
        getUserInfoUseCase = object : GetUserInfoUseCase {
            override fun invoke(): Flow<User> {
                throw IllegalStateException()
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
    fun `초기_데이터_로드_실패`() {
        // 토스트 메시지는 Compose Ui Testing에서는 감지하기가 어려움 직접 emulator에서 참고 할 것
        Thread.sleep(10000)
    }

    @Test
    fun `프로필_수정_실패`() {
        // 토스트 메시지는 Compose Ui Testing에서는 감지하기가 어려움 직접 emulator에서 참고 할 것
        fakeViewModel.editProfile()
        Thread.sleep(10000)
    }

    companion object {
        private const val TAG = "ProfileEditScreenErrorTest"
    }
}