package com.teampatch.core.data.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.teampatch.core.designsystem.R.drawable.img_upload_cert
import com.teampatch.core.domain.repository.NotificationRepository
import com.teampatch.daily.certify.EditCertifyActivity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationRepository {
    override fun scheduleCertifyNotification(time: LocalDateTime) {
        val delay = Duration.between(LocalDateTime.now(), time).toMillis()
        val request = OneTimeWorkRequestBuilder<CertifyNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(context).enqueue(request)
    }
}

class CertifyNotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // ✅ 1. 먼저 채널 생성
        createNotificationChannel(applicationContext)

        // ✅ 2. 알림 생성
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, "certify_channel")
            .setContentTitle("인증 시간이에요!")
            .setContentText("지금 바로 인증하러 가볼까요?")
            .setContentIntent(createPendingIntent())
            .setSmallIcon(img_upload_cert) // ← 아이콘도 꼭 지정해야 보임!
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
        return Result.success()
    }

    // ✅ 여기에 채널 생성 함수 추가
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "인증 알림"
            val description = "Daily certify notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("certify_channel", name, importance).apply {
                this.description = description
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(applicationContext, EditCertifyActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("FROM_NOTIFICATION", true)
        }

        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl,
    ): NotificationRepository
}