package com.teampatch.feature.settings.model

sealed interface SettingsSideEffect {

    data class LoadError(val t: Throwable) : SettingsSideEffect

    data object LogoutSuccess : SettingsSideEffect
    data class LogoutError(val t: Throwable) : SettingsSideEffect

    data object WithdrawFamilyGroupSuccess : SettingsSideEffect
    data class WithdrawFamilyGroupError(val t: Throwable) : SettingsSideEffect

    data object WithdrawAppSuccess : SettingsSideEffect
    data class WithdrawAppError(val t: Throwable) : SettingsSideEffect
}