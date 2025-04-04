package com.teampatch.harmony

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.usecase.authentication.IsLoginRequiredUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.harmony.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginRequiredUseCase: IsLoginRequiredUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
            flowErrorCatch(
                block = { isLoginRequiredUseCase() },
                action = {
                    _uiState.update { state ->
                        state.copy(
                            isFirstUser = true,
                            isLoginRequired = false,
                            isLoading = false,
                        )
                    }
                    it.printStackTrace()
                }
            )
                .collect { isLoginRequired ->
                    _uiState.update {
                        it.copy(
                            isFirstUser = if (uiState.value.isLoading) isLoginRequired else it.isFirstUser,
                            isLoginRequired = isLoginRequired,
                            isLoading = false,
                            hasGroup = getUserInfoUseCase().firstOrNull()?.groupId != -1
                        )
                    }
                }
        }
    }
}