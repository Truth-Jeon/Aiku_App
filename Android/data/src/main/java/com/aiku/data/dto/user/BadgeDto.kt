package com.aiku.data.dto.user

import com.aiku.domain.model.user.Badge
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BadgeDto(
    @Json(name = "titleName") val name: String?,
    @Json(name = "titleImg") val image: String?,
) {

    fun toBadge(): Badge = Badge(
        name = name ?: "",
        image = image ?: ""
    )

    companion object {
        val EMPTY = BadgeDto(
            name = "",
            image = ""
        )
    }
}

fun Badge.toBadgeDto() = BadgeDto(
    name = name,
    image = image
)