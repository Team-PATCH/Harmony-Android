package com.teampatch.core.data.repository

import android.content.Context
import com.teampatch.core.data.service.MemoryCardRecorderService
import com.teampatch.core.domain.fake.FakeMemoryCardQuestion
import com.teampatch.core.domain.model.MemoryCardQuestion
import com.teampatch.core.domain.repository.MemoryCardRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

internal class MemoryCardRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val memoryCardRecorderService: MemoryCardRecorderService
) : MemoryCardRepository {

    override fun startRecord() {
        val fileName = "${appContext.cacheDir.absolutePath}/${UUID.randomUUID()}.m4a"
        memoryCardRecorderService.prepare(fileName)
        memoryCardRecorderService.startRecording()
    }

    override fun stopRecording() {
        memoryCardRecorderService.stopRecording()
    }

    override fun resumeRecording() {
        memoryCardRecorderService.resumeRecording()
    }

    override fun pauseRecording() {
        memoryCardRecorderService.pauseRecording()
    }

    override fun getRecordingResult(): InputStream {
        return FileInputStream(memoryCardRecorderService.filePath)
    }

    override suspend fun addCommunication(
        memoryCardId: String,
        question: String,
        audioFile: InputStream
    ) {
        withContext(Dispatchers.IO) {
            audioFile.close()
        }
    }

    override suspend fun getQuestionMessage(memoryCardId: String): MemoryCardQuestion {
        return FakeMemoryCardQuestion().get()
    }
}