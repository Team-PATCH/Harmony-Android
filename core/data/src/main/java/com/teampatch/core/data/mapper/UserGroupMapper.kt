package com.teampatch.core.data.mapper

import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.UserGroup
import com.teampatch.core.network.model.group.response.UserGroupListQueryResponse

fun UserGroupListQueryResponse.Groups.toDomain(): UserGroup = UserGroup(
    groupId = groupId.toString(),
    name = name,
    members = members.map { member ->
        FamilyInfo(
            title = member.alias ?: "",
            name = member.nick,
            isManager = false,
            role = if (member.permissionId == "v") Role.VIP else Role.MEMBER,
            profileImageUrl = member.profile
        )
    }
)