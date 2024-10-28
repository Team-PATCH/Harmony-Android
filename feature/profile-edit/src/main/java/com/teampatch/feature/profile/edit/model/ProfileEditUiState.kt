package com.teampatch.feature.profile.edit.model

import com.teampatch.core.domain.model.Image

data class ProfileEditUiState(
    val relation: String = "",
    val name: String = "",
    val profileImage: Image? = null,
    val isLoading: Boolean = true
)