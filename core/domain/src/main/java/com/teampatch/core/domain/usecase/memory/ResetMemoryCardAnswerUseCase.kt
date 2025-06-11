package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.repository.MemoryCardRepository
import javax.inject.Inject

class ResetMemoryCardAnswerUseCase @Inject constructor(
    private val repository: MemoryCardRepository,
) {
    suspend operator fun invoke(card: MemoryCard) {
        val updatedCard = card.copy(text = "") // 답변 초기화
//        repository.updateMemoryCard(updatedCard)
    }
}