package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.repository.MemoryCardRepository
import javax.inject.Inject

class AddMemoryCardRecordingUseCase @Inject constructor(
    private val memoryCardRepository: MemoryCardRepository
) {

    suspend operator fun invoke(
        memoryCardId: String,
        question: String,
        isRecordFinished: () -> Boolean
    ) {
        val audioFile = memoryCardRepository.startRecord(isRecordFinished)
        memoryCardRepository.addCommunication(memoryCardId, question, audioFile)
    }
}