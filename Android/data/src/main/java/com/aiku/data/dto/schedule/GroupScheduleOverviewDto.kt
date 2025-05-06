package com.aiku.data.dto.schedule

import com.aiku.domain.model.schedule.GroupScheduleOverview
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.Locale

@JsonClass(generateAdapter = true)
data class GroupScheduleOverviewDto(
    @Json(name = "scheduleId") val id: Long?,
    @Json(name = "scheduleName") val name: String?,
    @Json(name = "location") val location: LocationDto?,
    @Json(name = "scheduleTime") val time: LocalDateTime?,
    @Json(name = "scheduleStatus") val status: String?,
    @Json(name = "memberSize") val memberSize: Int?,
    @Json(name = "accept") val accept: Boolean?
) {

    fun toGroupScheduleOverview() = GroupScheduleOverview(
        id = id ?: 0,
        name = name ?: "",
        location = (location ?: LocationDto(null, null, null)).toLocation(),
        time = time ?: LocalDateTime.MIN,
        status = if (accept != true) ScheduleStatus.BEFORE_PARTICIPATION else ScheduleStatus.valueOf(status?.uppercase(Locale.ROOT) ?: "WAIT"),
        memberSize = memberSize ?: 0,
        accept = accept ?: false
    )
}