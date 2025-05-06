package com.aiku.data.dto.user

import com.aiku.domain.model.user.Member
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Profile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemberDto(
    @Json(name = "memberId") val id: Long?,
    @Json(name = "nickname") val nickname: String?,
    @Json(name = "memberProfile") val profile: ProfileDto?,
    @Json(name = "point") val point: Long?
) {

    fun toMember(): Member = Member(
        id = id ?: 0,
        nickname = nickname ?: "",
        profile = profile?.toProfile() ?: Profile(
            type = ProfileType.CHAR,
            image = "",
            character = ProfileCharacter.C01,
            background = ProfileBackground.GREEN
        ), point = point ?: 0
    )
}

fun Member.toMemberDto() = MemberDto(
    id = id,
    nickname = nickname,
    profile = profile.toProfileDto(),
    point = point
)