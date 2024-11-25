package com.teampatch.core.network

import com.teampatch.core.network.di.NetworkSingletonModule
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class UserRemoteDataSourceUnitTest {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setup() {
        initOkhttpClient()
        initRetrofit()
        initUserRemoteDataSource()
    }

    private fun initOkhttpClient() {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newReq = request.newBuilder()
                    .addHeader("Authorization", "Bearer Token") // TODO: 토큰 입력하기
                    .build()
                chain.proceed(newReq)
            }
            .build()
    }

    private fun initRetrofit() {
        retrofit = NetworkSingletonModule.provideRetrofit(
            okHttpClient = okHttpClient,
            converterFactory = NetworkSingletonModule.provideMoshi()
        )
    }

    private fun initUserRemoteDataSource() {
        userRemoteDataSource = NetworkSingletonModule.provideUserRemoteDataSource(retrofit)
    }

    @Test
    fun `로그인_테스트`(): Unit = runBlocking {
        userRemoteDataSource.getMyProfile()
    }
}