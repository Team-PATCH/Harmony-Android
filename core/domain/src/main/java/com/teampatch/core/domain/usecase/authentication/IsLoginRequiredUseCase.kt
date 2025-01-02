package com.teampatch.core.domain.usecase.authentication

import com.teampatch.core.domain.entity.TokenManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class IsLoginRequiredUseCase @Inject constructor(
    private val tokenManager: TokenManager,
) {

    operator fun invoke(): Flow<Boolean> = tokenManager.isTokenInvalidListener
        .map { true }
        .onStart { emit(tokenManager.getAccessToken() == "") }
}