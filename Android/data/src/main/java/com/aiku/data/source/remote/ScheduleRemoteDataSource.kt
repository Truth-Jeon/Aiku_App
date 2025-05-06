package com.aiku.data.source.remote

import android.util.Log
import com.aiku.data.api.remote.ScheduleApi
import com.aiku.data.dto.IdDto
import com.aiku.data.dto.schedule.GroupScheduleOverviewPaginationDto
import com.aiku.data.dto.schedule.LocationDto
import com.aiku.data.dto.schedule.UserScheduleOverviewDto
import com.aiku.data.dto.schedule.UserScheduleOverviewPaginationDto
import com.aiku.data.dto.schedule.ScheduleDto
import com.aiku.data.dto.schedule.request.BetAkuReqDto
import com.aiku.data.dto.schedule.request.CreateScheduleReqDto
import com.aiku.domain.model.schedule.type.ScheduleStatus
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

class ScheduleRemoteDataSource @Inject constructor(
    private val scheduleApi: ScheduleApi
) {

    suspend fun createSchedule(groupId: Long, createScheduleRequest: CreateScheduleReqDto): IdDto {
        return scheduleApi.createSchedule(groupId, createScheduleRequest)
    }

    suspend fun fetchGroupSchedules(
        groupId: Long,
        page: Int,
        startDate: LocalDateTime,
        endTime: LocalDateTime
    ): GroupScheduleOverviewPaginationDto {
        return scheduleApi.fetchGroupSchedules(groupId, page, startDate, endTime)
    }

    suspend fun fetchGroupScheduleDetail(groupId: Long, scheduleId: Long): ScheduleDto {
        return scheduleApi.fetchGroupScheduleDetail(groupId, scheduleId)
    }

    suspend fun bet(scheduleId: Long, betAkuRequest: BetAkuReqDto) {
        return scheduleApi.bet(scheduleId, betAkuRequest)
    }


    suspend fun fetchUserSchedules(
        page: Int,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): UserScheduleOverviewPaginationDto {
        //TODO : return scheduleApi.fetchUserSchedules(page, startDate, endDate)

        return UserScheduleOverviewPaginationDto(
            page = page,
            runningSchedule = 0,
            waitingSchedule = 0,
            userScheduleOverview = List(11) { index ->
                // 임의의 더미 데이터 생성
                UserScheduleOverviewDto(
                    groupId = index.toLong(),
                    groupName = "Dummy Group $index",
                    scheduleId = index.toLong(),
                    scheduleName = "Dummy Schedule $index",
                    location = LocationDto(123.1 + Random.nextDouble(-0.01, 0.01), 123.1 + Random.nextDouble(-0.01, 0.01), "Dummy Location"),
                    time = LocalDateTime.now().plusHours(Random.nextLong(-24, 24)), // 현재 시간 기준으로 랜덤 시간 생성
                    status = listOf("RUN", "TERM", "WAIT").random(), // 세 가지 상태 중 랜덤 선택
                    memberSize = Random.nextInt(1, 10) // 1부터 10 사이의 랜덤 멤버 수
                )
            }
        )
    }
}
