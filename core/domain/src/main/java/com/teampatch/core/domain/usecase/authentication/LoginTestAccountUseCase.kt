package com.teampatch.core.domain.usecase.authentication

import com.teampatch.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginTestAccountUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke() {
        authenticationRepository.loginTestAccount()
    }
}