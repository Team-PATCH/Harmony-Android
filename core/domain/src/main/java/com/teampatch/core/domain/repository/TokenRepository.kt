package com.teampatch.core.domain.repository

interface TokenRepository {

    fun getAccessToken(): String
    fun setAccessToken(token: String)
}