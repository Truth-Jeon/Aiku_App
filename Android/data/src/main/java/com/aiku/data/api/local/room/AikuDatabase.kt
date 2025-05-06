package com.aiku.data.api.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aiku.data.entity.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1)
abstract class AikuDatabase : RoomDatabase() {

    abstract fun notificationDao(): NotificationDao
}