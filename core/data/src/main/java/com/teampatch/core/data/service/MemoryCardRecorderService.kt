package com.teampatch.core.data.service

import android.media.MediaRecorder
import com.teampatch.core.data.di.annotation.MemoryCardRecorder
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
internal class MemoryCardRecorderService @Inject constructor(
    @MemoryCardRecorder
    private val mediaRecorder: MediaRecorder,
) {

    lateinit var filePath: String
        private set

    fun prepare(outputFilePath: String) = with(mediaRecorder) {
        filePath = outputFilePath
        setOutputFile(outputFilePath)
        prepare()
    }

    fun startRecording() = with(mediaRecorder) {
        start()
    }

    fun resumeRecording() {
        mediaRecorder.resume()
    }

    fun pauseRecording() {
        mediaRecorder.pause()
    }

    fun stopRecording() = with(mediaRecorder) {
        stop()
        release()
    }
}