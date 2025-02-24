package com.teampatch.core.data.repository

import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.UserEntity
import com.teampatch.core.common.BuildConfig
import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.data.mapper.toEntity
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserOfflineRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    override fun getUserInfo(): Flow<User> = userDao.getMyUserData()
        .map { it.toDomain() }
        .retryWhen { cause, _ ->
            if (IS_LOGGED_IN_DEBUG_MODE && cause is NullPointerException) {
                val emptyUserEntity = User.createEmptyUser().copy(uid = "0").toEntity()
                userDao.insertUsers(emptyUserEntity)
                return@retryWhen true
            }
            false
        }

    override suspend fun editProfile(
        name: String?,
        profileImageUri: String?,
    ): Unit = withContext(Dispatchers.IO) {
        var userEntity: UserEntity = userDao.getMyUserData().first()

        if (name != null) {
            userEntity = userEntity.copy(name = name)
        }

        if (profileImageUri != null) {
            userEntity = userEntity.copy(profileImageUri = profileImageUri)
        }

        userDao.updateUser(userEntity = userEntity)
    }

    companion object {
        @Suppress("KotlinConstantConditions")
        private const val IS_LOGGED_IN_DEBUG_MODE: Boolean =
            BuildConfig.BUILD_TYPE == "loggedInDebug"
    }
}