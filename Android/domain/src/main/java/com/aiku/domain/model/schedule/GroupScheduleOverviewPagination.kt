package com.aiku.domain.model.schedule

data class GroupScheduleOverviewPagination(
    val page: Int,
    val groupId: Long,
    val runningSchedule: Int,
    val waitingSchedule: Int,
    val groupScheduleOverview: List<GroupScheduleOverview>
)