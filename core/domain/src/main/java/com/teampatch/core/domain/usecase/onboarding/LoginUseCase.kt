package com.teampatch.core.domain.usecase.onboarding

import com.teampatch.core.domain.model.FamilyGroup
import javax.inject.Inject

class LoginUseCase @Inject constructor() {

    suspend operator fun invoke(invitationCode: String): FamilyGroup {
        return FamilyGroup(
            memberCount = 0,
            profileImageUrl = emptyList(),
            madeByName = "test",
            madeByTitle = "test"
        )
    }
}