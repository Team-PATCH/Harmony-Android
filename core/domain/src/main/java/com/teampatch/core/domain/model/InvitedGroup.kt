package com.teampatch.core.domain.model

data class InvitedGroup(
    val groupId: Int,
    val groupManagerInfo: GroupManagerInfo,
    val users: List<User>,
) {

    data class GroupManagerInfo(
        val name: String,
    )

    data class User(
        val imageUrl: String?,
    )
}