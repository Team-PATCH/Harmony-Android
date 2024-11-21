package com.teampatch.core.data.service

import android.media.MediaRecorder
import com.teampatch.core.data.di.annotation.MemoryCardRecorder
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
internal class MemoryCardRecorderService @Inject constructor(
    @MemoryCardRecorder
    private val mediaRecorder: MediaRecorder
) {

    lateinit var filePath: String
        private set

    fun startRecording(outputFilePath: String) = with(mediaRecorder) {
        filePath = outputFilePath
        setOutputFile(outputFilePath)
        prepare()
        start()
    }

    fun stopRecording() = with(mediaRecorder) {
        stop()
        release()
    }

}