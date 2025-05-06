package com.aiku.presentation.state.group

import com.aiku.domain.model.group.GroupOverviewPagination
import java.time.LocalDateTime


data class GroupOverviewState(
    val id: Long,
    val name: String,
    val memberSize: Int,
    val lastScheduleTime: LocalDateTime,  // 2024-01-01T00:00:00
)


fun GroupOverviewPagination.GroupOverview.toGroupOverviewState(): GroupOverviewState =
    GroupOverviewState(
        id = id,
        name = name,
        memberSize = memberSize,
        lastScheduleTime = lastScheduleTime
    )