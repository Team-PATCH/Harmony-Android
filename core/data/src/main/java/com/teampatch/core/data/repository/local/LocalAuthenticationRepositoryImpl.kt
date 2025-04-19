package com.teampatch.core.data.repository.local

import com.agvber.core.authentication.kakao.KakaoLoginService
import com.harmony.core.database.dao.UserDao
import com.teampatch.core.domain.entity.SocialLoginHelper
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.model.LoginResult
import com.teampatch.core.domain.repository.AuthenticationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

internal class LocalAuthenticationRepositoryImpl @Inject constructor(
    private val kakaoLoginService: KakaoLoginService,
    private val tokenManager: TokenManager,
    private val userDao: UserDao,
    private val socialLoginHelper: SocialLoginHelper,
) : AuthenticationRepository {

    override suspend fun loginKakao(): LoginResult {
        val token = kakaoLoginService.login()
        socialLoginHelper.setSocialUserId(token.userId)
        val user = userDao.getUsers().firstOrNull()?.find { it.snsId == token.userId }
        return LoginResult(groupId = user?.groupId?.toString() ?: "-1")
    }

    override suspend fun logout() {
        tokenManager.setAccessToken("")
    }
}