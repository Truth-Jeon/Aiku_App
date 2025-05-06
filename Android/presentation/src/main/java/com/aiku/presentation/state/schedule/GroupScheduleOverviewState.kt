package com.aiku.presentation.state.schedule

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.schedule.GroupScheduleOverview
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.serializer.LocalDateTimeAsStringSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
@Immutable
data class GroupScheduleOverviewState(
    val id: Long,
    val name: String,
    override val location: LocationState,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    override val time: LocalDateTime,
    override val status: ScheduleStatus,
    override val memberSize: Int,
    val accept: Boolean
) : ScheduleOverviewState

fun GroupScheduleOverview.toGroupScheduleOverviewState() =
    GroupScheduleOverviewState(id, name, location.toLocationState(), time, status, memberSize, accept)