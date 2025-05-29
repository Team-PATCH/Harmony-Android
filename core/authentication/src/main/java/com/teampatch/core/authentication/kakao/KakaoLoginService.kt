package com.teampatch.core.authentication.kakao

import android.content.Context
import android.content.Intent
import com.teampatch.core.authentication.SocialLoginService
import com.teampatch.core.authentication.model.Token
import com.teampatch.core.common.getCustomParcelableExtra
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class KakaoLoginService @Inject constructor(
    @ApplicationContext private val appContext: Context,
) : SocialLoginService() {

    override suspend fun login(): Token = suspendCancellableCoroutine { continuation ->
        KakaoLoginHelperActivity.startActivityForResult(
            context = appContext,
            intent = Intent(appContext, KakaoLoginHelperActivity::class.java)
        ) { _: Int, data: Intent? ->
            val token = data?.getCustomParcelableExtra(
                name = KakaoLoginHelperActivity.TOKEN_PARAM,
                clazz = Token::class.java
            )

            if (token == null) {
                continuation.resumeWithException(IllegalStateException())
                return@startActivityForResult
            }

            continuation.resume(token)
        }
    }
}