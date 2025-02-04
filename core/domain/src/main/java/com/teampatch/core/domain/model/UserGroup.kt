package com.teampatch.core.domain.model

data class UserGroup(
    val groupId: String,
    val name: String,
    val members: List<FamilyInfo>,
)