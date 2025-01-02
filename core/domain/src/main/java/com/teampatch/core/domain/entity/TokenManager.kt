package com.teampatch.core.domain.entity

import kotlinx.coroutines.flow.Flow

abstract class TokenManager {

    abstract val isTokenInvalidListener: Flow<Unit>

    abstract fun getAccessToken(): String
    abstract fun setAccessToken(token: String)

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
    }
}