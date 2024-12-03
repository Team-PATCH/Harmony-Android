package com.agvber.core.authentication.kakao

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.agvber.core.authentication.model.Token
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

internal class KakaoLoginHelperActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            loginKakaoWeb(
                onSuccess = { finishActivityWithResult(it) },
                onFailure = { finish() }
            )
            finish()
            return
        }

        loginKakaoTalk(
            onSuccess = { finishActivityWithResult(it) },
            onFailure = {
                loginKakaoWeb(
                    onSuccess = { finishActivityWithResult(it) },
                    onFailure = { finish() }
                )
            }
        )
    }

    private fun finishActivityWithResult(token: Token) {
        val intent = Intent().apply {
            putExtra(TOKEN_PARAM, token)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun loginKakaoWeb(
        onSuccess: (Token) -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        try {
            UserApiClient.instance.loginWithKakaoAccount(
                context = this@KakaoLoginHelperActivity,
                callback = { token, error ->
                    if (error != null || token == null) {
                        Log.e(TAG, "카카오계정으로 로그인 실패", error)
                        onFailure(error)
                        return@loginWithKakaoAccount
                    }
                    UserApiClient.instance.me { user, error ->
                        if (user == null || error == null) {
                            Log.e(TAG, "카카오 유저정보 로드 실패", error)
                        }
                        onSuccess(handleCallbackToken(token, user))
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    private fun loginKakaoTalk(
        onSuccess: (Token) -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        try {
            UserApiClient.instance.loginWithKakaoTalk(
                context = this@KakaoLoginHelperActivity,
                callback = { token: OAuthToken?, error: Throwable? ->
                    if (error != null || token == null) {
                        Log.e("kakao", "카카오톡 로그인 실패", error)
                        onFailure(error)
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.me { user, error ->
                        if (user == null || error == null) {
                            Log.i(TAG, "카카오 유저정보 로드 실패")
                        }
                        onSuccess(handleCallbackToken(token, user))
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    private fun handleCallbackToken(
        token: OAuthToken,
        user: User?,
    ): Token {
        Log.i("kakao", "카카오계정으로 로그인 성공")
        return Token(
            userId = user?.kakaoAccount?.email ?: "null",
            nickname = user?.kakaoAccount?.name ?: "null",
            profileUrl = user?.kakaoAccount?.profile?.profileImageUrl ?: "null",
            authProvider = PROVIDER,
            accessToken = token.accessToken,
            accessTokenExpiresAt = token.accessTokenExpiresAt,
            refreshToken = token.refreshToken,
            refreshTokenExpiresAt = token.refreshTokenExpiresAt
        )
    }

    companion object {
        private const val TAG = "KakaoLoginHelperActivity"
        private const val PROVIDER = "kakao"
        internal const val TOKEN_PARAM = "token"
    }
}