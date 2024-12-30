package com.teampatch.core.data.mapper

import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import com.teampatch.core.network.model.user.ProfileResponse

internal fun ProfileResponse.toDomain(): User {
    val role = when (user.permissionId) {
        "v" -> Role.VIP
        "m" -> Role.MEMBER
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