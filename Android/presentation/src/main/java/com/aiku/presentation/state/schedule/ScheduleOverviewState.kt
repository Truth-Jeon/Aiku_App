package com.aiku.presentation.state.schedule

import android.os.Parcelable
import com.aiku.domain.model.schedule.type.ScheduleStatus
import java.time.LocalDateTime

interface ScheduleOverviewState : Parcelable {
    val location: LocationState
    val time: LocalDateTime
    val status: ScheduleStatus
    val memberSize: Int
}