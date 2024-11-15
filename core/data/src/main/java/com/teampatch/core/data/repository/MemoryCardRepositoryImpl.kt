package com.teampatch.core.data.repository

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.teampatch.core.common.checkRadioAudioPermission
import com.teampatch.core.common.exception.PermissionDeniedException
import com.teampatch.core.domain.fake.FakeMemoryCardQuestion
import com.teampatch.core.domain.model.MemoryCardQuestion
import com.teampatch.core.domain.repository.MemoryCardRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

internal class MemoryCardRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : MemoryCardRepository {

    override suspend fun startRecord(
        isFinished: () -> Boolean
    ): InputStream = withContext(Dispatchers.IO) {
        if (!appContext.checkRadioAudioPermission()) {
            throw PermissionDeniedException()
        }

        val fileName = "${appContext.cacheDir.absolutePath}/${UUID.randomUUID()}.m4a"

        val mediaRecorder: MediaRecorder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            MediaRecorder()
        } else {
            MediaRecorder(appContext)
        }
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(fileName)
                prepare()
                start()
            }

        while (!isFinished()) {
            delay(300)
        }

        mediaRecorder.stop()
        mediaRecorder.release()
        return@withContext FileInputStream(fileName)
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