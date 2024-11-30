package com.teampatch.core.domain.usecase.token

import com.teampatch.core.domain.repository.TokenRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    operator fun invoke(): String {
        return tokenRepository.getAccessToken()
    }
}