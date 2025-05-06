package com.aiku.domain.model.schedule

import com.aiku.domain.model.user.Member
import java.time.LocalDateTime

data class Schedule(
    val id: Long,
    val name: String,
    val location: Location,
    val time: LocalDateTime,
    val members: List<Member>,
    val createdAt: LocalDateTime
)