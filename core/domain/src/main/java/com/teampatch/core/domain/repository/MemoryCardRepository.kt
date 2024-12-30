package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.MemoryCardQuestion
import java.io.InputStream

interface MemoryCardRepository {

    fun startRecord()

    fun stopRecording()

    fun resumeRecording()

    fun pauseRecording()

    fun getRecordingResult(): InputStream

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

    /**
     * 해당 추억카드 질문들을 가져오는 함수입니다.
     */

    suspend fun getQuestionMessage(memoryCardId: String): MemoryCardQuestion
}