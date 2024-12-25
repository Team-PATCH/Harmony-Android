package com.teampatch.core.domain.model

data class User(
    val uid: String,
    val groupId: Int,
    val name: String,
    val relation: String,
    val profileImageUrl: String?,
    val role: Role
) {
    companion object {

        fun createEmptyUser(): User = User(
            uid = "",
            groupId = 0,
            name = "",
            relation = "",
            profileImageUrl = null,
            role = Role.MEMBER
        )
    }
}