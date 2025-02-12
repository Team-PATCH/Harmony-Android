package com.teampatch.feature.memorycard.registration.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import java.time.LocalDateTime
import kotlin.random.Random

class AudioRecorderHelper(private val context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    private lateinit var audioContentValues: ContentValues
    private lateinit var audioRecordFileUri: Uri
    private lateinit var audioRecordFile: ParcelFileDescriptor
    private lateinit var audioMediaRecorder: MediaRecorder

    fun prepare() {
        audioContentValues = buildAudioContentValues()
        audioRecordFileUri = getAudioFileUri()!!
        audioRecordFile = contentResolver.openFileDescriptor(audioRecordFileUri, "w")!!
        audioMediaRecorder = getMediaRecorderInstance(audioRecordFile)
    }

    fun start() {
        audioMediaRecorder.start()
    }

    fun stop() {
        audioMediaRecorder.stop()
    }

    fun release() {
        audioMediaRecorder.release()
        clearAudioContentValues()
    }

    fun getResultRecordFile(): Uri = audioRecordFileUri

    private fun buildAudioContentValues(): ContentValues = ContentValues().apply {
        put(
            MediaStore.Audio.Media.DISPLAY_NAME,
            "${LocalDateTime.now()}_${Random.nextInt(1000, 9999)}"
        )
        put(MediaStore.Audio.Media.MIME_TYPE, AUDIO_AAC_MIME_TYPE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    private fun clearAudioContentValues() = with(contentResolver) {
        audioContentValues.clear()
        audioContentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        update(audioRecordFileUri, audioContentValues, null, null)
    }

    private fun getAudioFileUri(): Uri? = with(contentResolver) {
        return insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioContentValues)
    }

    private fun getMediaRecorderInstance(
        audioFileDescriptor: ParcelFileDescriptor,
    ): MediaRecorder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        MediaRecorder()
    } else {
        MediaRecorder(context)
    }
        .apply {
            setOutputFile(audioFileDescriptor.fileDescriptor)
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
        }

    companion object {
        private const val AUDIO_AAC_MIME_TYPE = "audio/aac"
    }
}