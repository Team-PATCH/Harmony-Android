package com.teampatch.core.domain.usecase.daily

import com.teampatch.core.domain.repository.NotificationRepository
import java.time.LocalDateTime
import javax.inject.Inject

class ScheduleCertifyNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    operator fun invoke(time: LocalDateTime) {
        notificationRepository.scheduleCertifyNotification(time)
    }
}