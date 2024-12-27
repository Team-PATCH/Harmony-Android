package com.teampatch.feature.family.info.model

import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.User

data class FamilyInfoUiState(
    val user: User = User.createEmptyUser(),
    val familyInfo: List<FamilyInfo> = emptyList(),
    val isLoading: Boolean = true,
)
