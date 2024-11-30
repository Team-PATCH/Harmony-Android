package com.teampatch.core.network

import com.teampatch.core.network.di.NetworkSingletonModule
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class UserRemoteDataSourceUnitTest {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private val token = ""

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
                    .addHeader("Authorization", "Bearer $token") // TODO: 토큰 입력하기
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
    fun `회원가입&로그인_테스트`(): Unit = runBlocking {
        val body = SignupOrLoginRequestBody(
            "yeojeong@naver.com",
            "윤여정",
            "profile.png",
            "kakao",
            "kakao_social_token_example",
            "kakao_refresh_token_example",
            "2024-08-08 02:44:07"

        )
        val response = userRemoteDataSource.signupOrLogin(body)
        println("회원가입&로그인_테스트: $response")
    }

    @Test
    fun `사용자_정보_조회_테스트`(): Unit = runBlocking {
        val response = userRemoteDataSource.getMyProfile()
        println("사용자_정보_조회_테스트: $response")
    }
}