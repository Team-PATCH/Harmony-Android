package com.teampatch.harmony

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.usecase.authentication.IsLoginRequiredUseCase
import com.teampatch.core.domain.usecase.authentication.LoginTestAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val isLoginRequiredUseCase: IsLoginRequiredUseCase,
    private val loginTestAccountUseCase: LoginTestAccountUseCase,
) : ViewModel() {

    val isAppInitFinished: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(IS_APP_INIT_FINISHED, false)

    @Suppress("KotlinConstantConditions")
    val isLoginRequiredFlow: Flow<Boolean> = flowErrorCatch(
        block = {
            isLoginRequiredUseCase()
                .map { isRequired ->
                    if (isRequired && BuildConfig.BUILD_TYPE == "loggedInDebug") {
                        loginTestAccountUseCase()
                        return@map false
                    }
                    savedStateHandle[IS_APP_INIT_FINISHED] = true
                    isRequired
                }
        }
    ) { t ->
        t.printStackTrace()
        emit(false)
    }

    companion object {
        private const val IS_APP_INIT_FINISHED = "is_app_init_finished"
    }
}