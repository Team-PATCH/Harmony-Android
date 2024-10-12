package com.teampatch.core.domain.model

data class FamilyGroup(
    val memberCount: Int,
    val profileImageUrl: List<String>,
    val madeByName: String,
    val madeByTitle: String,
)
