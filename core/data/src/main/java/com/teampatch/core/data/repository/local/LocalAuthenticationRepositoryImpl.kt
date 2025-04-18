package com.teampatch.core.data.repository.local

import com.agvber.core.authentication.kakao.KakaoLoginService
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.model.LoginResult
import com.teampatch.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

internal class LocalAuthenticationRepositoryImpl @Inject constructor(
    private val kakaoLoginService: KakaoLoginService,
    private val tokenManager: TokenManager,
) : AuthenticationRepository {

    override suspend fun loginKakao(): LoginResult {
        kakaoLoginService.login()
        return LoginResult(groupId = "-1")
    }

    override suspend fun logout() {
        tokenManager.setAccessToken("")
    }

}