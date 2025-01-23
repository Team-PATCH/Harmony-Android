package com.teampatch.harmony

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.usecase.authentication.IsLoginRequiredUseCase
import com.teampatch.harmony.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginRequiredUseCase: IsLoginRequiredUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
            flowErrorCatch(
                block = { isLoginRequiredUseCase() },
                action = { it.printStackTrace() }
            )
                .collect { isLoginRequired ->
                    _uiState.update {
                        it.copy(
                            isLoginRequired = isLoginRequired,
                            isLoading = false
                        )
                    }
                }
        }
    }
}