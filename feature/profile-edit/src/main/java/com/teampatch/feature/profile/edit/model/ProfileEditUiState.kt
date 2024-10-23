package com.teampatch.feature.profile.edit.model

import com.teampatch.core.domain.model.Image

sealed interface ProfileEditUiState {

    data object Init : ProfileEditUiState

    data class Error(val t: Throwable) : ProfileEditUiState

    data class Success(
        val relation: String,
        val name: String,
        val profileImage: Image?
    ) : ProfileEditUiState
}