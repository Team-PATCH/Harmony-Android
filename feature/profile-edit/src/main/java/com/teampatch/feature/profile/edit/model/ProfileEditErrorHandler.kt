package com.teampatch.feature.profile.edit.model

sealed interface ProfileEditErrorHandler {

    data class ProfileEditError(val t: Throwable) : ProfileEditErrorHandler
}