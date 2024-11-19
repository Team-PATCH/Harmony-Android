package com.teampatch.core.data.service

import android.media.MediaRecorder
import com.teampatch.core.common.SingletonInstanceHelper
import com.teampatch.core.data.di.annotation.MemoryCardRecorder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MemoryCardRecorderService @Inject constructor(
    @MemoryCardRecorder
    private val mediaRecorder: SingletonInstanceHelper<MediaRecorder>
) {

    lateinit var filePath: String
        private set

    fun startRecording(
        outputFilePath: String
    ) = with(mediaRecorder.getInstance()) {
        filePath = outputFilePath
        setOutputFile(outputFilePath)
        prepare()
        start()
    }

    fun stopRecording() = with(mediaRecorder.getInstance()) {
        stop()
        release()
        mediaRecorder.resetInstance()
    }

}