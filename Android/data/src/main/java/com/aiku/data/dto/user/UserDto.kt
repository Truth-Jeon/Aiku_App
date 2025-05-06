package com.aiku.data.dto.user

import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Badge
import com.aiku.domain.model.user.Profile
import com.aiku.domain.model.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @see com.aiku.data.UserEntity
 */
@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "memberId") val id: Long?,
    @Json(name = "nickname") val nickname: String?,
    @Json(name = "kakaoId") val kakaoId: String?,
    @Json(name = "memberProfile") val profile: ProfileDto?,
    @Json(name = "title") val badge: BadgeDto?,
    @Json(name = "point") val point: Int?,
) {

    fun toUser(): User = User(
        id = id ?: 0,
        nickname = nickname ?: "",
        kakaoId = kakaoId ?: "",
        profile = profile?.toProfile() ?: Profile(
            type = ProfileType.CHAR,
            image = "",
            character = ProfileCharacter.C01,
            background = ProfileBackground.GREEN
        ),
        badge = badge?.toBadge() ?: Badge(
            name = "",
            image = ""
        ),
        point = point ?: 0
    )

    companion object {
        val EMPTY = UserDto(
            id = 0,
            nickname = "",
            kakaoId = "",
            profile = ProfileDto.EMPTY,
            badge = BadgeDto.EMPTY,
            point = 0
        )
    }
}

fun User.toUserDto() = UserDto(
    id = id,
    nickname = nickname,
    kakaoId = kakaoId,
    profile = profile.toProfileDto(),
    badge = badge.toBadgeDto(),
    point = point
)