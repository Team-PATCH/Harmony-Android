package com.teampatch.core.domain.usecase.onboarding

import com.teampatch.core.domain.exception.FamilyRegistrationRequiredException
import com.teampatch.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginKakaoUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    @Throws(FamilyRegistrationRequiredException::class)
    suspend operator fun invoke() {
        val loginResult = authenticationRepository.loginKakao()

        if (loginResult.groupId == "-1") {
            throw FamilyRegistrationRequiredException()
        }
    }
}