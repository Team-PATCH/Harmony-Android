package com.teampatch.core.domain.repository

import androidx.paging.PagingData
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.model.MemoryCardQuestion
import java.io.InputStream
import kotlinx.coroutines.flow.Flow

interface MemoryCardRepository {

    /**
     * 추억카드 의사소통 과정을 Server 보내기 위한 함수입니다.
     *
     * @param[question] 오디오 녹음에 대한 질문을 입력해주세요.
     */

    suspend fun addCommunication(
        memoryCardId: String,
        question: String,
        audioFile: InputStream,
    )

    suspend fun deleteMemoryCard(memoryCardId: String)

//    suspend fun updateMemoryCard(card: MemoryCard)
    suspend fun addAnswer(
        memoryCardId: String,
        answer: String,
    )

    /**
     * 해당 추억카드 질문들을 가져오는 함수입니다.
     */

    suspend fun getQuestionMessage(memoryCardId: String): MemoryCardQuestion

    fun getMemoryCards(): Flow<PagingData<MemoryCard>>

    fun getMemoryCardById(memoryCardId: String): Flow<MemoryCard>
}