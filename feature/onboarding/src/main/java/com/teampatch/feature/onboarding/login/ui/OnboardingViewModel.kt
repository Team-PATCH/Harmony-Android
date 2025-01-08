package com.teampatch.feature.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.exception.FamilyRegistrationRequiredException
import com.teampatch.core.domain.model.FamilyGroup
import com.teampatch.core.domain.model.Host
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.InvitationMessage
import com.teampatch.core.domain.usecase.onboarding.LoginKakaoUseCase
import com.teampatch.core.domain.usecase.onboarding.LoginUseCase
import com.teampatch.core.domain.usecase.onboarding.RegisterFamilyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val loginUseCase: LoginUseCase, // 이것도 언젠간 쓰여야할 것 같은데..
    private val registerFamilyUseCase: RegisterFamilyUseCase,
) : ViewModel() {

    // 로그인 성공 상태
    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful

    /**
     * 에러 메시지가 필요한지 고민이 필요함
     */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 로그인 시도를 수행하는 함수
    fun loginKakao() {
        viewModelScope.launch {
            runCatching {
//                loginKakaoUseCase()
            }.onSuccess {
                _isLoginSuccessful.value = true
            }.onFailure { throwable ->
                _isLoginSuccessful.value = false
                _errorMessage.value = throwable.message
                // Handle specific exceptions like FamilyRegistrationRequiredException
                if (throwable is FamilyRegistrationRequiredException) {
                    _errorMessage.value = "Family registration required."
                }
            }
        }
    }

    // 가족 등록을 수행하는 함수
    fun registerFamily(
        host: Host,
        relation: String,
        name: String,
        profileImage: Image,
    ): LiveData<Result<InvitationMessage>> = liveData {
        emit(
            Result.runCatching {
                registerFamilyUseCase(
                    host = host,
                    relation = relation,
                    name = name,
                    profileImage = profileImage
                )
            }
        )
    }

}