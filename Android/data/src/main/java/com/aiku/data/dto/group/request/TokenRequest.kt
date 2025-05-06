package com.aiku.data.dto.group.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IssueATRTRequest(
    @Json(name = "idToken") val idToken: String
)

@JsonClass(generateAdapter = true)
data class IssueATRequest(
    @Json(name = "refreshToken") val refreshToken: String,
    @Json(name = "accessToken") val accessToken: String
)