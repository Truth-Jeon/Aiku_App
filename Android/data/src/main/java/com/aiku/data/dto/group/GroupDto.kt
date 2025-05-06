package com.aiku.data.dto.group

import com.aiku.data.dto.user.MemberDto
import com.aiku.domain.model.group.Group
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupDto(
    @Json(name = "groupId") val id: Long?,
    @Json(name = "groupName") val name: String?,
    @Json(name = "members") val members: List<MemberDto>?,
) {
    fun toGroup(): Group = Group(
        id = id ?: 0,
        name = name ?: "",
        members = members?.map { it.toMember() } ?: emptyList()
    )
}