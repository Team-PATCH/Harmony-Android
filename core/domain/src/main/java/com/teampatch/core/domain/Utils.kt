package com.teampatch.core.domain

import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User

val emptyUser = User(
    uid = "",
    name = "",
    relation = "",
    profileImageUrl = null,
    role = Role.MEMBER
)