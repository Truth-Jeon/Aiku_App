package com.aiku.domain.model.schedule.request

import com.aiku.domain.model.schedule.Location
import java.time.LocalDateTime

data class CreateScheduleReq(
    val scheduleName: String,
    val location: Location,
    val scheduleTime: LocalDateTime,
    val pointAmount: Int
)