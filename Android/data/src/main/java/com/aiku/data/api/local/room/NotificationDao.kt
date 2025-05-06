package com.aiku.data.api.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aiku.data.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notification")
    fun getNotifications(): Flow<List<NotificationEntity>>
}