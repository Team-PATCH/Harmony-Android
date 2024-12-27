package com.teampatch.core.data.repository

import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import com.teampatch.core.network.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    private val user: MutableStateFlow<User?> = MutableStateFlow(null)

    override fun getUserInfo(): Flow<User> = user.map {
        if (it == null) {
            val userResponse = userRemoteDataSource.getMyProfile()
            return@map user.updateAndGet { userResponse.toDomain() }!!
        }
        it
    }
}