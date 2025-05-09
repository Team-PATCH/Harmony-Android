package com.teampatch.feature.onboarding.enter.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.usecase.group.JoinFamilyGroupUseCase
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import com.teampatch.core.domain.usecase.user.RegisterAppUseCase
import com.teampatch.feature.onboarding.enter.model.OnboardingEnterInvitationCodeEvent
import com.teampatch.feature.onboarding.enter.model.OnboardingEnterInvitationCodeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class OnboardingEnterInvitationCodeViewModel @Inject constructor(
    private val joinFamilyGroupUseCase: JoinFamilyGroupUseCase,
    private val registerAppUseCase: RegisterAppUseCase,

    private val editProfileUseCase: EditProfileUseCase,
) : ViewModel() {

    private val _onboardingEnterInvitationCodeEvent: Channel<OnboardingEnterInvitationCodeEvent> =
        Channel()
    val onboardingEnterInvitationCodeEvent: Flow<OnboardingEnterInvitationCodeEvent> =
        _onboardingEnterInvitationCodeEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<OnboardingEnterInvitationCodeUiState> =
        MutableStateFlow(OnboardingEnterInvitationCodeUiState())
    val uiState: StateFlow<OnboardingEnterInvitationCodeUiState> = _uiState.asStateFlow()

    private var isRegistering: Boolean = false

    /** 이미지 업로드 */

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri.asStateFlow()

    fun updateProfileImage(value: Uri) {
        _profileImageUri.value = value
    }

    override fun onCleared() {
        profileImageUri.value?.let {
            viewModelScope.launch {
                editProfileUseCase(null, it.toString())
            }
        }
        super.onCleared()
    }

    /** 여기까지 */

    fun updateInviteCode(inviteCode: String) {
        _uiState.update { it.copy(inviteCode = inviteCode) }
    }

    fun joinGroup() {
        if (uiState.value.isProgress) return
        viewModelScope.launch {
            val inviteCode = uiState.value.inviteCode

            // ↓ 실패 체크 무시하고 그냥 진행 (주석 처리)
            // require(inviteCode.toIntOrNull() != null)

            _uiState.update { it.copy(isProgress = true) }

            // ↓ 실패 여부 신경 쓰지 않고 그냥 실행만 함
            try {
                joinFamilyGroupUseCase(inviteCode)
            } catch (e: Exception) {
                e.printStackTrace()
                // ↓ 실패 이벤트도 무시하고 전송 안 함 (주석 처리)
                // _onboardingEnterInvitationCodeEvent.send(OnboardingEnterInvitationCodeEvent.Error(e))
            }

            // ↓ 성공 여부도 무시하고 전송 안 함 (주석 처리)
            // _onboardingEnterInvitationCodeEvent.send(OnboardingEnterInvitationCodeEvent.Success)

            _uiState.update { it.copy(isProgress = false) }
        }
    }

    fun registerMemberProfile(relation: String, name: String) = viewModelScope.launch {
        try {
            if (isRegistering) return@launch

            isRegistering = true
            _uiState.update { it.copy(isProgress = true) }

            registerAppUseCase(name, relation, null, Role.MEMBER)

            _onboardingEnterInvitationCodeEvent.send(OnboardingEnterInvitationCodeEvent.Success)
        } catch (e: Exception) {
            e.printStackTrace()
            _onboardingEnterInvitationCodeEvent.send(OnboardingEnterInvitationCodeEvent.Error(e))
        }
    }.invokeOnCompletion {
        isRegistering = false
        _uiState.update { it.copy(isProgress = false) }
    }
}