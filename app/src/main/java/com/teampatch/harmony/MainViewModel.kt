package com.teampatch.harmony

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.usecase.authentication.IsLoginRequiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val isLoginRequiredUseCase: IsLoginRequiredUseCase,
) : ViewModel() {

    val isAppInitFinished: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(IS_APP_INIT_FINISHED, false)

    val isLoginRequiredFlow: Flow<Boolean> = flowErrorCatch(
        block = {
            isLoginRequiredUseCase()
                .onEach { savedStateHandle[IS_APP_INIT_FINISHED] = true }
        }
    ) { t ->
        t.printStackTrace()
        emit(false)
    }

    companion object {
        private const val IS_APP_INIT_FINISHED = "is_app_init_finished"
    }
}