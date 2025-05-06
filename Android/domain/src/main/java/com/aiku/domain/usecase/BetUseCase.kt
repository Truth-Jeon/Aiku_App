package com.aiku.domain.usecase

import com.aiku.domain.exception.BET_AMOUNT_NOT_POSITIVE
import com.aiku.domain.exception.ErrorResponse
import com.aiku.domain.model.schedule.request.BetAkuReq
import com.aiku.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BetUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {

    operator fun invoke(scheduleId: Long, beteeMemberId: Long, pointAmount: Int): Flow<Unit> {
        return flow {
            if (pointAmount <= 0)
                throw ErrorResponse(BET_AMOUNT_NOT_POSITIVE)
            emit(Unit)
        }.flatMapLatest {
            scheduleRepository.bet(scheduleId, BetAkuReq(beteeMemberId, pointAmount))
        }
    }
}