package com.teampatch.core.data.manager

import com.teampatch.core.domain.usecase.token.GetAccessTokenUseCase
import com.teampatch.core.network.utils.TokenManager
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : TokenManager {

    override fun getAccessToken(): String {
        return getAccessTokenUseCase()
    }
}