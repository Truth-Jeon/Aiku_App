package com.aiku.domain.model.schedule

import com.aiku.domain.model.schedule.type.ScheduleStatus
import java.time.LocalDateTime

data class GroupScheduleOverview(
    val id: Long,
    val name: String,
    val location: Location,
    val time: LocalDateTime,
    val status: ScheduleStatus,
    val memberSize: Int,
    val accept: Boolean
)