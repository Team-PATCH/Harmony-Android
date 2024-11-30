package com.teampatch.core.domain.usecase.token

import com.teampatch.core.domain.repository.TokenRepository
import javax.inject.Inject

class SetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    operator fun invoke(token: String) {
        tokenRepository.setAccessToken(token)
    }
}