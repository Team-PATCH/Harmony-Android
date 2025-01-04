package com.teampatch.feature.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.model.FamilyGroup
import com.teampatch.core.domain.model.Host
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.InvitationMessage
import com.teampatch.core.domain.usecase.onboarding.LoginUseCase
import com.teampatch.core.domain.usecase.onboarding.RegisterFamilyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerFamilyUseCase: RegisterFamilyUseCase,
) : ViewModel() {

    // 로그인 성공 상태
    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful

    // 알림 권한 상태
    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted: StateFlow<Boolean> = _isPermissionGranted

    // 로그인 시도를 수행하는 함수
    fun login(invitationCode: String) {
        viewModelScope.launch {
            runCatching {
                loginUseCase(invitationCode)
            }.onSuccess {
                _isLoginSuccessful.value = true
            }.onFailure {
                _isLoginSuccessful.value = false
                // Handle login failure (e.g., show error message)
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

    // 알림 권한 상태를 업데이트하는 함수
    fun updatePermissionStatus(granted: Boolean) {
        _isPermissionGranted.value = granted
    }
}