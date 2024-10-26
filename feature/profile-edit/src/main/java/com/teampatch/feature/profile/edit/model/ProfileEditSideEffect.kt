package com.teampatch.feature.profile.edit.model

sealed interface ProfileEditSideEffect {

    data object Init : ProfileEditSideEffect
    data object Load : ProfileEditSideEffect
    data class LoadError(val t: Throwable) : ProfileEditSideEffect

    data class ProfileEditError(val t: Throwable) : ProfileEditSideEffect

    data object ProfileEditSuccess : ProfileEditSideEffect
}