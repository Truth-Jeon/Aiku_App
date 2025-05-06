package com.aiku.data.dto.schedule

import com.aiku.data.dto.user.MemberDto
import com.aiku.domain.model.schedule.Location
import com.aiku.domain.model.schedule.Schedule
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ScheduleDto(
    @Json(name = "scheduleId") val id: Long?,
    @Json(name = "scheduleName") val name: String?,
    @Json(name = "location") val location: LocationDto?,
    @Json(name = "scheduleTime") val time: LocalDateTime?,
    @Json(name = "members") val members: List<MemberDto>?,
    @Json(name = "createdAt") val createdAt: LocalDateTime?
) {

    fun toSchedule(): Schedule = Schedule(
        id = id ?: 0,
        name = name ?: "",
        location = location?.toLocation() ?: Location(.0, .0, ""),
        time = time ?: LocalDateTime.now(),
        members = members?.map { it.toMember() } ?: emptyList(),
        createdAt = createdAt ?: LocalDateTime.now()
    )
}