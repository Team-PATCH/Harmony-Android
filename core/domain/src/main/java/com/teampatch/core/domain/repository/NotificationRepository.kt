package com.teampatch.core.domain.repository

import java.time.LocalDateTime

interface NotificationRepository {
    fun scheduleCertifyNotification(time: LocalDateTime)
}