package com.aiku.data.entity

import com.aiku.data.UserEntity
import com.aiku.domain.model.user.User

/**
 * @see com.aiku.data.UserEntity
 */
fun User.toUserEntity(): UserEntity {
    return UserEntity.getDefaultInstance().toBuilder()
        .setMemberId(id)
        .setNickname(nickname)
        .setKakaoId(kakaoId)
        .setProfile(profile.toProfileEntity())
        .setBadge(badge.toBadgeEntity())
        .setPoint(point)
        .build()
}

fun UserEntity.toUser(): User {
    return User(
        id = memberId,
        nickname = nickname,
        kakaoId = kakaoId,
        profile = profile.toProfile(),
        badge = badge.toBadge(),
        point = point
    )
}