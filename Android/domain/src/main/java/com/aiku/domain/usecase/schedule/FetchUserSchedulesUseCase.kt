package com.aiku.domain.usecase.schedule

import androidx.paging.PagingData
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class FetchUserSchedulesUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    operator fun invoke(startDate: LocalDateTime, endDate: LocalDateTime, isToday : Boolean): Flow<PagingData<UserScheduleOverview>> {
        return scheduleRepository.fetchUserSchedules(startDate, endDate, isToday)
    }
}
