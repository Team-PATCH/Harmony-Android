package com.teampatch.feature.family.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.family.GetFamilyInfoUseCase
import com.teampatch.core.domain.usecase.family.InviteFamilyUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.family.info.model.FamilyInfoErrorHandler
import com.teampatch.feature.family.info.model.FamilyInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyInfoViewModel @Inject constructor(
    getUserInfoUseCase: GetUserInfoUseCase,
    getFamilyInfoUseCase: GetFamilyInfoUseCase,
    private val inviteFamilyUseCase: InviteFamilyUseCase
) : ViewModel() {

    val familyInfoUiState: StateFlow<FamilyInfoUiState> = kotlin.runCatching {
        combine(getUserInfoUseCase(), getFamilyInfoUseCase()) { user, family ->
            FamilyInfoUiState.Success(
                user = user,
                familyInfo = family
            )
        }
    }
        .getOrElse {
            flowOf(FamilyInfoUiState.Error(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FamilyInfoUiState.Init
        )

    private val _familyInfoErrorHandler: MutableSharedFlow<FamilyInfoErrorHandler> = MutableSharedFlow()
    val familyInfoErrorHandler = _familyInfoErrorHandler.asSharedFlow()

    fun inviteFamily() = viewModelScope.launch {
        try {
            inviteFamilyUseCase()
        } catch (e: Exception) {
            e.printStackTrace()
            _familyInfoErrorHandler.emit(FamilyInfoErrorHandler.InviteError(e))
        }
    }

}