package com.aiku.data.dto.schedule

import com.aiku.domain.model.schedule.GroupScheduleOverview
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.Locale

@JsonClass(generateAdapter = true)
data class UserScheduleOverviewDto(
    @Json(name = "groupId") val groupId: Long?,
    @Json(name = "groupName") val groupName: String?,
    @Json(name = "scheduleId") val scheduleId: Long?,
    @Json(name = "scheduleName") val scheduleName: String?,
    @Json(name = "location") val location: LocationDto?,
    @Json(name = "scheduleTime") val time: LocalDateTime?,
    @Json(name = "scheduleStatus") val status: String?,
    @Json(name = "memberSize") val memberSize: Int?
) {

    fun toUserScheduleOverview() = UserScheduleOverview(
        groupId = groupId ?: 0,
        groupName = groupName ?: "",
        scheduleId = scheduleId ?: 0,
        scheduleName = scheduleName ?: "",
        location = (location ?: LocationDto(null, null, null)).toLocation(),
        time = time ?: LocalDateTime.MIN,
        status = ScheduleStatus.valueOf(status?.uppercase(Locale.ROOT) ?: "WAIT"),
        memberSize = memberSize ?: 0
    )
}