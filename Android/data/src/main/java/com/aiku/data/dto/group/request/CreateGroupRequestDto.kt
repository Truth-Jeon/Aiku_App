package com.aiku.data.dto.group.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateGroupRequestDto(
    @Json(name = "groupName") val groupName: String
)