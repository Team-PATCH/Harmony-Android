package com.teampatch.core.domain.usecase.authentication

import com.teampatch.core.domain.entity.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class IsLoginRequiredUseCase @Inject constructor(
    private val tokenManager: TokenManager
) {

    operator fun invoke(): Flow<Boolean> {
        return flowOf(tokenManager.getAccessToken() == "")
    }
}