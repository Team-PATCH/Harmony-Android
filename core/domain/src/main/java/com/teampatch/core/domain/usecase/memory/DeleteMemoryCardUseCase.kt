package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.repository.MemoryCardRepository
import javax.inject.Inject

class DeleteMemoryCardUseCase @Inject constructor(
    private val repository: MemoryCardRepository,
) {
    suspend operator fun invoke(memoryCardId: String) {
        repository.deleteMemoryCard(memoryCardId)
    }
}