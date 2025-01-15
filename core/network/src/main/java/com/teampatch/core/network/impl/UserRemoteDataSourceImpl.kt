package com.teampatch.core.network.impl

import com.teampatch.core.network.UserRemoteDataSource
import com.teampatch.core.network.model.user.ProfileResponse
import com.teampatch.core.network.model.user.SignupOrLoginRequestBody
import com.teampatch.core.network.model.user.SignupOrLoginResponse
import com.teampatch.core.network.service.UserNetworkService
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val userNetworkService: UserNetworkService,
) : UserRemoteDataSource {

    override suspend fun signupOrLogin(
        signupOrLoginRequestBody: SignupOrLoginRequestBody,
    ): SignupOrLoginResponse = userNetworkService.signupOrLogin(signupOrLoginRequestBody)

    override suspend fun getMyProfile(): ProfileResponse = userNetworkService.getMyProfile()
}