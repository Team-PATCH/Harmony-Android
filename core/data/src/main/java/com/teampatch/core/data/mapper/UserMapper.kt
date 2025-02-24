package com.teampatch.core.data.mapper

import com.harmony.core.database.model.UserEntity
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import com.teampatch.core.network.model.user.ProfileResponse

private const val MEMBER = "m"
private const val VIP = "v"

internal fun ProfileResponse.toDomain(): User {
    val role = when (user.permissionId) {
        VIP -> Role.VIP
        MEMBER -> Role.MEMBER
        else -> throw IllegalStateException()
    }

    return User(
        uid = user.userId,
        groupId = user.groupId,
        name = user.nick,
        relation = "",
        profileImageUrl = null,
        role = role
    )
}

internal fun UserEntity.toDomain(): User {
    return User(
        uid = uid.toString(),
        groupId = groupId,
        name = name,
        relation = relation,
        profileImageUrl = profileImageUri,
        role = when (role) {
            MEMBER -> Role.MEMBER
            VIP -> Role.VIP
            else -> throw IllegalArgumentException()
        }
    )
}

internal fun User.toEntity(): UserEntity {
    return UserEntity(
        uid = uid.toLong(),
        groupId = groupId,
        name = name,
        relation = relation,
        profileImageUri = profileImageUrl,
        role = when (role) {
            Role.VIP -> VIP
            Role.MEMBER -> MEMBER
        }
    )
}