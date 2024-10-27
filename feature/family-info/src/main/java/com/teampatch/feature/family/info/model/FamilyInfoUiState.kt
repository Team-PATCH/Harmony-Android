package com.teampatch.feature.family.info.model

import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User

data class FamilyInfoUiState(
    val user: User = User(uid = "", name = "", profileImageUrl = null, role = Role.MEMBER),
    val familyInfo: List<FamilyInfo> = emptyList(),
    val isLoading: Boolean = true,
)
