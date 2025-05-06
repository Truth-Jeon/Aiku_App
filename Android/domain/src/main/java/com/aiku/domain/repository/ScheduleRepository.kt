package com.aiku.domain.repository

import androidx.paging.PagingData
import com.aiku.domain.model.schedule.GroupScheduleOverviewPagination
import com.aiku.domain.model.schedule.Schedule
import com.aiku.domain.model.schedule.request.BetAkuReq
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.model.schedule.UserScheduleOverviewPagination
import com.aiku.domain.model.schedule.request.CreateScheduleReq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

interface ScheduleRepository {
    fun createSchedule(groupId: Long, createScheduleReq: CreateScheduleReq): Flow<Long>
    fun fetchGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<GroupScheduleOverviewPagination>
    fun fetchGroupScheduleDetail(groupId: Long, scheduleId: Long): Flow<Schedule>
    fun bet(scheduleId: Long, betAkuReq: BetAkuReq): Flow<Unit>

    fun fetchUserSchedules(startDate: LocalDateTime, endDate: LocalDateTime, isToday : Boolean): Flow<PagingData<UserScheduleOverview>>

}