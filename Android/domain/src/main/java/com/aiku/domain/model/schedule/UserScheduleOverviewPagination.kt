package com.aiku.domain.model.schedule

data class UserScheduleOverviewPagination(
    val page: Int,
    val runningSchedule: Int,
    val waitingSchedule: Int,
    val userScheduleOverview: List<UserScheduleOverview>
)