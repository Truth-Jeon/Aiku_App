package com.aiku.domain.repository

import androidx.paging.PagingData
import com.aiku.domain.model.group.Group
import com.aiku.domain.model.group.GroupOverviewPagination
import com.aiku.domain.model.schedule.UserScheduleOverview
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GroupRepository {
    fun createGroup(name: String): Flow<Group>
    fun deleteGroup(groupId: Long): Flow<Unit>
    fun fetchGroup(groupId: Long): Flow<Group>
    fun enterGroup(groupId: Long): Flow<Unit>
    fun exitGroup(groupId: Long): Flow<Unit>

    fun fetchGroups(): Flow<PagingData<GroupOverviewPagination.GroupOverview>>
}