package com.teampatch.core.domain.usecase.profile

import com.teampatch.core.domain.model.Image

interface EditProfileUseCase {

    suspend operator fun invoke(
        relation: String,
        name: String,
        profileImage: Image?
    )
}