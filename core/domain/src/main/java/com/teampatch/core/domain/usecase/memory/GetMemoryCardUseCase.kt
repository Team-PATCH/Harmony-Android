package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.fake.FakeMemoryCard
import com.teampatch.core.domain.model.MemoryCard
import javax.inject.Inject

class GetMemoryCardUseCase @Inject constructor() {

    suspend operator fun invoke(memoryCardId: String): MemoryCard {
        return FakeMemoryCard().get().first()
    }
}