package com.teampatch.feature.family.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.family.GetFamilyInfoUseCase
import com.teampatch.core.domain.usecase.family.InviteFamilyUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.family.info.model.FamilyInfoSideEffect
import com.teampatch.feature.family.info.model.FamilyInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getFamilyInfoUseCase: GetFamilyInfoUseCase,
    private val inviteFamilyUseCase: InviteFamilyUseCase
) : ViewModel() {

    init {
        load()
    }

    private val _sideEffect: Channel<FamilyInfoSideEffect> = Channel()
    val sidEffect: Flow<FamilyInfoSideEffect> = _sideEffect.receiveAsFlow()

    private val _familyInfoUiState = MutableStateFlow(FamilyInfoUiState())
    val familyInfoUiState = _familyInfoUiState.asStateFlow()

    private fun load() = viewModelScope.launch {
        try {
            val user = getUserInfoUseCase().first()
            val familyInfo = getFamilyInfoUseCase().first()
            _familyInfoUiState.value = FamilyInfoUiState(user, familyInfo)
            _sideEffect.send(FamilyInfoSideEffect.Load)
        } catch (e: Exception) {
            _sideEffect.send(FamilyInfoSideEffect.LoadError(e))
        }
    }


    fun inviteFamily() = viewModelScope.launch {
        try {
            inviteFamilyUseCase()
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(FamilyInfoSideEffect.InviteError(e))
        }
    }

}