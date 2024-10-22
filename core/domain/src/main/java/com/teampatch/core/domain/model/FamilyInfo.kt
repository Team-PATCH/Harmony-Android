package com.teampatch.core.domain.model

data class FamilyInfo(
    val title: String,
    val name: String,
    val isManager: Boolean,
    val role: Role
)
