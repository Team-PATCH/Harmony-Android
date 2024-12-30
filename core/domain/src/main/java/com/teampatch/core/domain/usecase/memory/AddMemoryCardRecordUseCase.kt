package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.repository.MemoryCardRepository
import javax.inject.Inject

class AddMemoryCardRecordUseCase @Inject constructor(
    private val memoryCardRepository: MemoryCardRepository,
) {

    suspend operator fun invoke(
        memoryCardId: String,
        question: String,
    ) {
        val audioFile = memoryCardRepository.getRecordingResult()
        memoryCardRepository.addCommunication(memoryCardId, question, audioFile)
    }
}