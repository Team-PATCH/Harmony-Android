package com.teampatch.feature.onboarding.enter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.group.JoinFamilyGroupUseCase
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
) : ViewModel() {

    private val _onboardingEnterInvitationCodeEvent: Channel<OnboardingEnterInvitationCodeEvent> =
        Channel()
    val onboardingEnterInvitationCodeEvent: Flow<OnboardingEnterInvitationCodeEvent> =
        _onboardingEnterInvitationCodeEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<OnboardingEnterInvitationCodeUiState> =
        MutableStateFlow(OnboardingEnterInvitationCodeUiState())
    val uiState: StateFlow<OnboardingEnterInvitationCodeUiState> = _uiState.asStateFlow()

    fun updateInviteCode(inviteCode: String) {
        _uiState.update { it.copy(inviteCode = inviteCode) }
    }

    fun joinGroup() {
        if (uiState.value.isProgress) return
        viewModelScope.launch {
            try {
                val inviteCode = uiState.value.inviteCode
                require(inviteCode.toIntOrNull() != null)

                _uiState.update { it.copy(isProgress = true) }
                joinFamilyGroupUseCase(inviteCode)

                _onboardingEnterInvitationCodeEvent.send(
                    OnboardingEnterInvitationCodeEvent.Success
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _onboardingEnterInvitationCodeEvent.send(
                    OnboardingEnterInvitationCodeEvent.Error(e)
                )
            } finally {
                _uiState.update { it.copy(isProgress = false) }
            }
        }
    }
}