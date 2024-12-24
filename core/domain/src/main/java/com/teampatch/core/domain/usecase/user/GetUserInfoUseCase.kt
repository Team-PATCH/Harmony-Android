package com.teampatch.core.domain.usecase.user

import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<User> =
        userRepository.getUserInfo()
}