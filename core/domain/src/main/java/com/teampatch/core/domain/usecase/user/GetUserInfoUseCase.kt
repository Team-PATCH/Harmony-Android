package com.teampatch.core.domain.usecase.user

import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

//    operator fun invoke(): Flow<User> = userRepository.getUserInfo()
    operator fun invoke(): Flow<User> = flowOf(User.createEmptyUser())
}