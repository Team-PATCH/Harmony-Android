package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.repository.MemoryCardRepository
import javax.inject.Inject

class PauseMemoryCardRecordingUseCase @Inject constructor(
    private val memoryCardRepository: MemoryCardRepository
) {

    operator fun invoke() {
        memoryCardRepository.pauseRecording()
    }
}