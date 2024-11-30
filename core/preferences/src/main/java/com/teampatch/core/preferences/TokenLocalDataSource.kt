package com.teampatch.core.preferences

interface TokenLocalDataSource {

    fun getAccessToken(): String
    fun setAccessToken(token: String)

    fun getRefreshToken(): String
    fun setRefreshToken(token: String)
}