package com.teampatch.core.tokenstore

interface TokenLocalDataSource {

    fun getAccessToken(): String
    fun setAccessToken(token: String)

    fun getRefreshToken(): String
    fun setRefreshToken(token: String)
}