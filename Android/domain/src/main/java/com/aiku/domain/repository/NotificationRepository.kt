package com.aiku.domain.repository

import com.aiku.domain.model.notification.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun insertNotification(notification: Notification)
    fun getNotifications(): Flow<List<Notification>>
}