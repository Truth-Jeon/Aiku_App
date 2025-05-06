package com.aiku.data.dto.schedule

import com.aiku.domain.model.schedule.UserScheduleOverviewPagination
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserScheduleOverviewPaginationDto(
    @Json(name = "page") val page: Int?,
    @Json(name = "runSchedule") val runningSchedule: Int?,
    @Json(name = "waitSchedule") val waitingSchedule: Int?,
    @Json(name = "data") val userScheduleOverview: List<UserScheduleOverviewDto>?
) {

    fun toUserScheduleOverviewPagination() = UserScheduleOverviewPagination(
        page = page ?: 1,
        runningSchedule = runningSchedule ?: 0,
        waitingSchedule = waitingSchedule ?: 0,
        userScheduleOverview = (userScheduleOverview?.map { it.toUserScheduleOverview() } ?: emptyList())
    )
}