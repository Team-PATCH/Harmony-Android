package com.teampatch.core.domain.usecase.onboarding

import com.teampatch.core.domain.model.FamilyGroup

interface LoginUseCase {

    suspend operator fun invoke(invitationCode: String): FamilyGroup
}