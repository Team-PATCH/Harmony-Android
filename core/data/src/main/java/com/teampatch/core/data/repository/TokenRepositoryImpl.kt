package com.teampatch.core.data.repository

import com.teampatch.core.domain.repository.TokenRepository
import com.teampatch.core.tokenstore.TokenLocalDataSource
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override fun getAccessToken(): String {
        return tokenLocalDataSource.getAccessToken()
    }

    override fun setAccessToken(token: String) {
        tokenLocalDataSource.setAccessToken(token)
    }
}