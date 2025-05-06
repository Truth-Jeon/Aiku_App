package com.aiku.domain.model.schedule

import com.aiku.domain.model.schedule.type.ScheduleStatus
import java.time.LocalDateTime

data class UserScheduleOverview(
    val groupId: Long,
    val groupName: String,
    val scheduleId: Long,
    val scheduleName: String,
    val location: Location,
    val time: LocalDateTime,
    val status: ScheduleStatus,
    val memberSize: Int
)