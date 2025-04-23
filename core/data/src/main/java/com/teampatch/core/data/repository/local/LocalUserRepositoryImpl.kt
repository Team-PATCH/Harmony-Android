package com.teampatch.core.data.repository.local

import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.UserEntity
import com.teampatch.core.data.mapper.MEMBER
import com.teampatch.core.data.mapper.VIP
import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.entity.SocialLoginHelper
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocalUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val socialLoginHelper: SocialLoginHelper,
) : UserRepository {

    override fun getUserInfo(): Flow<User> = userDao.getMyUserData().map {
        it.toDomain()
    }

    override suspend fun editProfile(name: String?, profileImageUri: String?) {
    }

    override suspend fun addUserProfile(
        name: String,
        relation: String,
        profileImageUrl: String?,
        role: Role,
    ) {
        val userEntity = UserEntity(
            uid = null,
            groupId = null,
            name = name,
            relation = relation,
            profileImageUri = role.name,
            role = when (role) {
                Role.VIP -> VIP
                Role.MEMBER -> MEMBER
            },
            snsId = socialLoginHelper.getSocialUserId(),
            isMe = true
        )
        userDao.insertUsers(userEntity)
    }
}