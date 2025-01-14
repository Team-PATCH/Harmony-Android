package com.teampatch.harmony

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.usecase.authentication.IsLoginRequiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val isLoginRequiredUseCase: IsLoginRequiredUseCase,
) : ViewModel() {

    val isAppInitFinished: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(IS_APP_INIT_FINISHED, false)

    val isLoginRequiredFlow: Flow<Boolean> =
        flowErrorCatch({ isLoginRequiredUseCase() }) { t ->
            t.printStackTrace()
            emit(false)
        }

    fun finishAppInit() {
        savedStateHandle[IS_APP_INIT_FINISHED] = true
    }

    companion object {
        private const val IS_APP_INIT_FINISHED = "is_app_init_finished"
    }
}