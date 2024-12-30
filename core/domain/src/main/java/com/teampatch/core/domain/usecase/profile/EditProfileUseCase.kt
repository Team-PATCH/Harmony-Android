package com.teampatch.core.domain.usecase.profile

import com.teampatch.core.domain.model.Image
import javax.inject.Inject

class EditProfileUseCase @Inject constructor() {

    suspend operator fun invoke(
        relation: String,
        name: String,
        profileImage: Image?,
    ) {
    }
}