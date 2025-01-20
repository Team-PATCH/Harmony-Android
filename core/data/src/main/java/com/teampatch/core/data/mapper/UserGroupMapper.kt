package com.teampatch.core.data.mapper

import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.UserGroup
import com.teampatch.core.network.model.group.response.UserGroupListQueryResponse

fun UserGroupListQueryResponse.toDomain() = groups.map { group ->
    UserGroup(
        groupId = group.groupId.toString(),
        name = group.name,
        members = group.members.map { member ->
            FamilyInfo(
                title = member.alias ?: "",
                name = member.nick,
                isManager = false,
                role = if (member.permissionId == "v") Role.VIP else Role.MEMBER,
                profileImageUrl = member.profile
            )
        }
    )
}