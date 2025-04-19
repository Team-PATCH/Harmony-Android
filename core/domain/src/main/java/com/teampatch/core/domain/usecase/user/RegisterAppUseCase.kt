package com.teampatch.core.domain.usecase.user

import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.repository.UserRepository
import javax.inject.Inject

class RegisterAppUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        relation: String,
        profileImageUrl: String?,
        role: Role
    ) {
        userRepository.addUserProfile(
            name = name,
            relation = relation,
            profileImageUrl = profileImageUrl,
            role = role
        )
    }
}