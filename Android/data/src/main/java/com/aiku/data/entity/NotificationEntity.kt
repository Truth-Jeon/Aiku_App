package com.aiku.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aiku.domain.model.notification.Notification
import com.aiku.domain.model.notification.NotificationCategory

@Entity(tableName = "notification")
data class NotificationEntity(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun toNotification() = Notification(
        category = NotificationCategory.valueOf(category),
        title = title,
        content = content,
        id = id
    )
}

fun Notification.toNotificationEntity() = NotificationEntity(
    category = category.name,
    title = title,
    content = content,
)