package com.teampatch.core.domain.model

data class User(
    val uid: String,
    val name: String,
    val relation: String,
    val profileImageUrl: String?,
    val role: Role
)