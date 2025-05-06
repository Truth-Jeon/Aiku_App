package com.aiku.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IdDto(
    @Json(name = "id") val id: Long?
)