package com.aiku.domain.usecase.group

import androidx.paging.PagingData
import com.aiku.domain.model.group.GroupOverviewPagination
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.repository.GroupRepository
import com.aiku.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class FetchGroupsUseCase(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(): Flow<PagingData<GroupOverviewPagination.GroupOverview>> {
        return groupRepository.fetchGroups()
    }
}
