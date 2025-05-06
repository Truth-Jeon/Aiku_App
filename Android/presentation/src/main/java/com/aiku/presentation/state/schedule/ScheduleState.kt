package com.aiku.presentation.state.schedule

import androidx.compose.runtime.Immutable
import com.aiku.domain.model.schedule.Schedule
import com.aiku.presentation.serializer.LocalDateTimeAsStringSerializer
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.toMemberState
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Immutable
data class ScheduleState(
    val id: Long,
    val name: String,
    val location: LocationState,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val time: LocalDateTime,
    val members: List<MemberState>,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val createdAt: LocalDateTime
)

fun Schedule.toGroupScheduleState() = ScheduleState(
    id,
    name,
    location.toLocationState(),
    time,
    members.map { it.toMemberState() },
    createdAt
)