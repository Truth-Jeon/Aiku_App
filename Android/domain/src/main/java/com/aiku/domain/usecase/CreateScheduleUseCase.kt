package com.aiku.domain.usecase

import com.aiku.domain.model.schedule.Location
import com.aiku.domain.model.schedule.request.CreateScheduleReq
import com.aiku.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class CreateScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {

    operator fun invoke(
        groupId: Long,
        scheduleName: String,
        location: Location,
        scheduleTime: LocalDateTime,
        pointAmount: Int
    ): Flow<Long> {
        return scheduleRepository.createSchedule(
            groupId,
            CreateScheduleReq(scheduleName, location, scheduleTime, pointAmount)
        )
    }
}