package com.aiku.presentation.state.notification

import com.aiku.domain.model.notification.Notification
import com.aiku.domain.model.notification.NotificationCategory

data class NotificationViewState(
    val category: NotificationCategory,
    val title: String,
    val content: String,
    val id: Long
) {

    fun toNotification() = Notification(
        category = category,
        title = title,
        content = content,
        id = id
    )
}

fun Notification.toNotificationViewState() = NotificationViewState(
    category = category,
    title = title,
    content = content,
    id = id
)