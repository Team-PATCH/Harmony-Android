package com.teampatch.core.domain.usecase.user

import com.teampatch.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {

    operator fun invoke(): Flow<User>
}