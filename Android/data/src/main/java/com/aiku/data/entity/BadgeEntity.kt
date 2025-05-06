package com.aiku.data.entity

import com.aiku.data.UserEntity
import com.aiku.domain.model.user.Badge

/**
 * @see com.aiku.data.UserEntity
 */
fun Badge.toBadgeEntity(): UserEntity.BadgeEntity {
    return UserEntity.BadgeEntity.getDefaultInstance().toBuilder()
        .setName(name)
        .setImg(image)
        .build()
}

fun UserEntity.BadgeEntity.toBadge(): Badge {
    return Badge(
        name = name,
        image = img
    )
}