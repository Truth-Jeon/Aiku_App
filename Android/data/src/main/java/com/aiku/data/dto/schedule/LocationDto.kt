package com.aiku.data.dto.schedule

import com.aiku.domain.model.schedule.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "latitude") val latitude: Double?,
    @Json(name = "longitude") val longitude: Double?,
    @Json(name = "locationName") val name: String?
) {

    fun toLocation() = Location(
        latitude = latitude ?: .0,
        longitude = longitude ?: .0,
        name = name ?: ""
    )
}

fun Location.toLocationDto() = LocationDto(
    latitude, longitude, name
)