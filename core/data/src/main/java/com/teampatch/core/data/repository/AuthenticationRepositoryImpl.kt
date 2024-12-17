package com.teampatch.core.data.repository

import com.agvber.core.authentication.kakao.KakaoLoginService
import com.teampatch.core.data.serverDateTimeFormatter
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.model.LoginResult
import com.teampatch.core.domain.repository.AuthenticationRepository
import com.teampatch.core.network.UserRemoteDataSource
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val kakaoLoginService: KakaoLoginService,
    private val tokenManager: TokenManager
) : AuthenticationRepository {

    override suspend fun loginKakao(): LoginResult {
        val socialToken = kakaoLoginService.login()
        val body = SignupOrLoginRequestBody(
            userId = socialToken.userId,
            nick = socialToken.nickname,
            profile = socialToken.profileUrl,
            authProvider = socialToken.authProvider,
            socialToken = socialToken.accessToken,
            refreshToken = socialToken.refreshToken,
            socialTokenExpiredAt = socialToken.accessTokenExpiresAt.toServerDateFormat()
        )

        val signupOrLoginResponse = userRemoteDataSource.signupOrLogin(body)
        tokenManager.setAccessToken(signupOrLoginResponse.token)

        return LoginResult(groupId = signupOrLoginResponse.user.groupId)
    }

    private fun Date.toServerDateFormat(): String {
        return LocalDateTime.ofInstant(
            toInstant(),
            ZoneId.systemDefault()
        )
            .format(serverDateTimeFormatter)
    }
}
