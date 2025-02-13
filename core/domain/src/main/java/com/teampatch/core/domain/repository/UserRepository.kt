package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfo(): Flow<User>

    suspend fun editProfile(
        name: String?,
        profileImageUri: String?,
    )
}