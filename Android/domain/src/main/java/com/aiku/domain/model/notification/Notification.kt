package com.aiku.domain.model.notification

data class Notification(
    val category: NotificationCategory,
    val title: String,
    val content: String,
    val id: Long
)