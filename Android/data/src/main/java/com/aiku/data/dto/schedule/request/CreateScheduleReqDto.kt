package com.aiku.data.dto.schedule.request

import com.aiku.data.dto.schedule.LocationDto
import com.aiku.data.dto.schedule.toLocationDto
import com.aiku.domain.model.schedule.request.CreateScheduleReq
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class CreateScheduleReqDto(
    @Json(name = "scheduleName") val scheduleName: String,
    @Json(name = "location") val location: LocationDto,
    @Json(name = "scheduleTime") val scheduleTime: LocalDateTime,
    @Json(name = "pointAmount") val pointAmount: Int
)

fun CreateScheduleReq.toCreateScheduleReqDto() = CreateScheduleReqDto(
    scheduleName, location.toLocationDto(), scheduleTime, pointAmount
)