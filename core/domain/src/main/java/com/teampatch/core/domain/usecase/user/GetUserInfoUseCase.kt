package com.teampatch.core.domain.usecase.user

import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetUserInfoUseCase @Inject constructor() {

    operator fun invoke(): Flow<User> = flowOf(
        User(
            uid = "uid001",
            name = "Alice Johnson",
            relation = "Mother",
            profileImageUrl = null,
            role = Role.VIP
        )
    )
}