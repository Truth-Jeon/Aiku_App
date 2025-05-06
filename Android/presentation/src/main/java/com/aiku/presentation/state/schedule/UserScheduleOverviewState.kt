package com.aiku.presentation.state.schedule

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.schedule.GroupScheduleOverview
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.model.schedule.type.ScheduleStatus
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Immutable
data class UserScheduleOverviewState(
    val groupId: Long,
    val groupName: String,
    val scheduleId: Long,
    val scheduleName: String,
    override val location: LocationState,
    override val time: LocalDateTime,
    override val status: ScheduleStatus,
    override val memberSize: Int
) : ScheduleOverviewState

fun UserScheduleOverview.toUserScheduleOverviewState() =
    UserScheduleOverviewState(groupId, groupName, scheduleId, scheduleName, location.toLocationState(), time, status, memberSize)