package com.aiku.data.repository

import androidx.paging.PagingData
import com.aiku.data.dto.group.request.CreateGroupRequestDto
import com.aiku.data.source.remote.GroupRemoteDataSource
import com.aiku.data.util.createPager
import com.aiku.domain.model.group.Group
import com.aiku.domain.model.group.GroupOverviewPagination
import com.aiku.domain.model.schedule.UserScheduleOverview
import com.aiku.domain.repository.GroupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource,
    private val coroutineDispatcher: CoroutineDispatcher

) : GroupRepository {

    override fun createGroup(name: String): Flow<Group> {
        return flow {
            emit(
                Group(
                    id = groupRemoteDataSource
                        .createGroup(CreateGroupRequestDto(groupName = name)).id ?: 0L,
                    name = name,
                    members = emptyList()
                )
            )
        }.flowOn(coroutineDispatcher)
    }

    override fun deleteGroup(groupId: Long): Flow<Unit> {
        return flow {
            emit(groupRemoteDataSource.deleteGroup(groupId))
        }.flowOn(coroutineDispatcher)
    }

    override fun fetchGroups(): Flow<PagingData<GroupOverviewPagination.GroupOverview>> {
        return createPager(
            pageSize = 10,
            loadData = { page ->
                val dto = groupRemoteDataSource.fetchGroups(page)
                val pagination = dto.toGroupOverviewPagination()
                pagination.data
            }
        )
    }

    override fun fetchGroup(groupId: Long): Flow<Group> {
        return flow {
            emit(groupRemoteDataSource.fetchGroup(groupId).toGroup())
        }.flowOn(coroutineDispatcher)
    }

    override fun enterGroup(groupId: Long): Flow<Unit> {
        return flow {
            emit(groupRemoteDataSource.enterGroup(groupId))
        }.flowOn(coroutineDispatcher)
    }

    override fun exitGroup(groupId: Long): Flow<Unit> {
        return flow {
            emit(groupRemoteDataSource.exitGroup(groupId))
        }.flowOn(coroutineDispatcher)
    }
}