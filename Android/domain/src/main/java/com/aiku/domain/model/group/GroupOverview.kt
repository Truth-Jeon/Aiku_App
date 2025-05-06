package com.aiku.domain.model.group

import java.time.LocalDateTime

data class GroupOverviewPagination(
    val page: Int,
    val data: List<GroupOverview>,
) {

    data class GroupOverview(
        val id: Long,
        val name: String,
        val memberSize: Int,
        val lastScheduleTime: LocalDateTime
    )
}