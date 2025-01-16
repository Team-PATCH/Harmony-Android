package com.teampatch.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.core.net.toUri
import com.teampatch.core.common.exception.FileTooLargeException
import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.repository.UserRepository
import com.teampatch.core.network.UserRemoteDataSource
import com.teampatch.core.network.model.FileUploadRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.withContext

@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {

    private val user: MutableStateFlow<User?> = MutableStateFlow(null)

    override fun getUserInfo(): Flow<User> = user.map {
        if (it == null) {
            val userResponse = userRemoteDataSource.getMyProfile()
            return@map user.updateAndGet { userResponse.toDomain() }!!
        }
        it
    }

    override suspend fun editProfile(name: String?, profileImageUri: String?) {
        val profileImageUploadFileRequest = profileImageUri?.toUri()?.let { uri ->
            val contextResolver = appContext.contentResolver
            contextResolver.query(
                uri,
                profileEditContentResolverProjections,
                null,
                null,
                null
            )?.use { cursor ->
                cursor.moveToFirst()
                val fileName = cursor.getString(0)
                val fileMediaType = cursor.getString(1)

                val profileImageInputStream = ByteArrayOutputStream().use { outputStream ->
                    withContext(Dispatchers.Default) {
                        val bitmap: Bitmap =
                            BitmapFactory.decodeStream(contextResolver.openInputStream(uri))
                        var quality: Int = PROFILE_IMAGE_MAX_QUALITY

                        while (true) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                            quality -= (quality * PROFILE_IMAGE_COMPRESS_RATIO).roundToInt()
                            outputStream.reset()

                            if (outputStream.size() <= PROFILE_IMAGE_LIMIT_SIZE) {
                                break
                            }

                            if (quality < PROFILE_IMAGE_MIN_QUALITY) {
                                throw FileTooLargeException()
                            }
                        }
                        bitmap.recycle()
                        ByteArrayInputStream(outputStream.toByteArray())
                    }
                }

                FileUploadRequest(
                    fileName = fileName,
                    fileMediaType = fileMediaType,
                    fileContent = profileImageInputStream
                )
            }
        }

        val profileResponse = userRemoteDataSource.editMyProfile(
            username = name,
            profileImage = profileImageUploadFileRequest
        )

        user.value = profileResponse.toDomain()
    }

    companion object {

        private const val PROFILE_IMAGE_MAX_QUALITY: Int = 90
        private const val PROFILE_IMAGE_MIN_QUALITY: Int = 5
        private const val PROFILE_IMAGE_LIMIT_SIZE: Int = 10485760
        private const val PROFILE_IMAGE_COMPRESS_RATIO: Double = 0.1

        private val profileEditContentResolverProjections: Array<String> by lazy {
            arrayOf(
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE
            )
        }
    }
}