package com.teampatch.core.network

import com.teampatch.core.network.annotation.AuthorizedRequest
import com.teampatch.core.network.model.user.ProfileResponse
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import com.teampatch.core.network.model.user.SignupOrLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserRemoteDataSource {

    @POST("/user/signup")
    suspend fun signupOrLogin(
        @Body body: SignupOrLoginRequestBody
    ): SignupOrLoginResponse

    @AuthorizedRequest
    @GET("/user/profile")
    suspend fun getMyProfile(): ProfileResponse
}