package com.teampatch.feature.profile.edit.model

import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.Role

data class ProfileEditUiState(
    val relation: String = "",
    val name: String = "",
    val profileImage: Image? = null,
    val role: Role = Role.MEMBER,
    val isLoading: Boolean = true
)