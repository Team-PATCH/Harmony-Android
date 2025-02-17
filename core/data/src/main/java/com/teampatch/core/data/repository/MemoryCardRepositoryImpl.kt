package com.teampatch.core.data.repository

import com.teampatch.core.domain.fake.FakeMemoryCardQuestion
import com.teampatch.core.domain.model.MemoryCardQuestion
import com.teampatch.core.domain.repository.MemoryCardRepository
import java.io.InputStream
import javax.inject.Inject

internal class MemoryCardRepositoryImpl @Inject constructor() : MemoryCardRepository {

    override suspend fun addCommunication(
        memoryCardId: String,
        question: String,
        audioFile: InputStream,
    ) {
    }

    override suspend fun getQuestionMessage(memoryCardId: String): MemoryCardQuestion = FakeMemoryCardQuestion().get()
}