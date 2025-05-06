package com.aiku.data.source.local

import com.aiku.data.api.local.room.AikuDatabase
import com.aiku.data.entity.NotificationEntity
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val database: AikuDatabase
) {

    private val dao = database.notificationDao()

    fun insertNotification(notification: NotificationEntity) {
        dao.insertNotification(notification)
    }

    fun getNotifications() = dao.getNotifications()
}