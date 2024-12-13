package com.teampatch.core.domain.entity

abstract class TokenManager {

    abstract fun getAccessToken(): String
    abstract fun setAccessToken(token: String)

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
    }
}