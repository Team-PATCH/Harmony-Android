package com.teampatch.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.authentication.LogoutAppUseCase
import com.teampatch.core.domain.usecase.authentication.WithdrawAppUseCase
import com.teampatch.core.domain.usecase.authentication.WithdrawFamilyUseCase
import com.teampatch.core.domain.usecase.version.GetAppLatestVersionUseCase
import com.teampatch.feature.settings.model.SettingsSideEffect
import com.teampatch.feature.settings.model.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAppLatestVersionUseCase: GetAppLatestVersionUseCase,
    private val logoutAppUseCase: LogoutAppUseCase,
    private val withdrawAppUseCase: WithdrawAppUseCase,
    private val withdrawFamilyUseCase: WithdrawFamilyUseCase
) : ViewModel() {

    private val _sideEffect = Channel<SettingsSideEffect>()
    val sideEffect: Flow<SettingsSideEffect> = _sideEffect.receiveAsFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val settingsUiState: StateFlow<SettingsUiState> = kotlin.runCatching {
        getAppLatestVersionUseCase().mapLatest {
            SettingsUiState(
                isLatestVersion = it.isLatest,
                installedVersion = it.installedVersionName,
                isLoading = false
            )
        }
    }
        .getOrElse {
            _sideEffect.trySend(SettingsSideEffect.LoadError(it))
            emptyFlow()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState()
        )

    fun logout() = viewModelScope.launch {
        try {
            logoutAppUseCase()
            _sideEffect.send(SettingsSideEffect.LogoutSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(SettingsSideEffect.LogoutError(e))
        }
    }

    fun withdrawFamilyGroup() = viewModelScope.launch {
        try {
            withdrawFamilyUseCase()
            _sideEffect.send(SettingsSideEffect.WithdrawFamilyGroupSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(SettingsSideEffect.WithdrawFamilyGroupError(e))
        }
    }

    fun withdrawApp() = viewModelScope.launch {
        try {
            withdrawAppUseCase()
            _sideEffect.send(SettingsSideEffect.WithdrawAppSuccess)
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffect.send(SettingsSideEffect.WithdrawAppError(e))
        }
    }

    override fun onCleared() {
        super.onCleared()
        _sideEffect.close()
    }
}