package com.teampatch.core.network.interceptor

import com.teampatch.core.tokenstore.TokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class TokenInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val request = request()
        val accessToken = tokenLocalDataSource.getAccessToken()

        if (accessToken.isEmpty()) {
            return@with proceed(request)
        }

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        proceed(newRequest)
    }
}