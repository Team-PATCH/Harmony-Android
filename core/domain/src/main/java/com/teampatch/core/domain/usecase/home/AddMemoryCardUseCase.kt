package com.teampatch.core.domain.usecase.home

import com.teampatch.core.domain.model.Image
import java.time.LocalDateTime
import javax.inject.Inject

class AddMemoryCardUseCase @Inject constructor() {

    suspend operator fun invoke(
        memories: String,
        dateTime: LocalDateTime,
        image: Image,
    ) {

    }
}