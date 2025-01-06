package com.teampatch.core.domain.usecase.authentication

import com.teampatch.core.domain.entity.TokenManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class IsLoginRequiredUseCase @Inject constructor(
    private val tokenManager: TokenManager,
) {

    operator fun invoke(): Flow<Boolean> = tokenManager.isTokenInvalidListener
}