package com.teampatch.core.network

import com.teampatch.core.network.model.user.ProfileResponse
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import com.teampatch.core.network.model.user.SignupOrLoginResponse

interface UserRemoteDataSource {

    suspend fun signupOrLogin(
        signupOrLoginRequestBody: SignupOrLoginRequestBody
    ): SignupOrLoginResponse

    suspend fun getMyProfile(): ProfileResponse
}