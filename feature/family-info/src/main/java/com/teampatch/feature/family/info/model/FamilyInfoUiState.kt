package com.teampatch.feature.family.info.model

import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.User

sealed interface FamilyInfoUiState {

    data object Init : FamilyInfoUiState

    data class Success(
        val user: User,
        val familyInfo: List<FamilyInfo>
    ) : FamilyInfoUiState

    data class Error(val t: Throwable?) : FamilyInfoUiState
}