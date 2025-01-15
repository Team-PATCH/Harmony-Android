package com.teampatch.core.network

import com.teampatch.core.network.impl.UserRemoteDataSourceImpl
import com.teampatch.core.network.model.FileUploadRequest
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import java.io.File
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class UserRemoteDataSourceUnitTest {

    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var userRemoteDataSource: UserRemoteDataSource

        @JvmStatic
        @BeforeClass
        fun setup() {
            retrofit = TestRetrofit.getRetrofit()
            initUserRemoteDataSource()
        }

        private fun initUserRemoteDataSource() {
            userRemoteDataSource = UserRemoteDataSourceImpl(retrofit.create())
        }
    }

    @Test
    fun `회원가입&로그인_테스트`(): Unit = runBlocking {
        val body = SignupOrLoginRequestBody(
            userId = "yeojeong@naver.com",
            nick = "윤여정",
            profile = "profile.png",
            authProvider = "kakao",
            socialToken = "kakao_social_token_example",
            refreshToken = "kakao_refresh_token_example",
            socialTokenExpiredAt = "2024-08-08 02:44:07"

        )
        val response = userRemoteDataSource.signupOrLogin(body)
        println("회원가입&로그인_테스트: ${response.pretty()}")
    }

    @Test
    fun `사용자_정보_조회_테스트`(): Unit = runBlocking {
        val response = userRemoteDataSource.getMyProfile()
        println("사용자_정보_조회_테스트: ${response.pretty()}")
    }

    @Test
    fun `사용자_프로필 수정`(): Unit = runBlocking {
        val name: String? = null
        val file = File("C:\\Users\\MinJun\\Desktop\\th.jpg")
        val profileImage = FileUploadRequest(
            fileName = file.name,
            fileMediaType = null,
            fileContent = file.inputStream()
        )
        val response = userRemoteDataSource.editMyProfile(name, profileImage)
        println("사용자_프로필 수정: ${response.pretty()}")
    }
}