package com.aiku.data.source.remote

import com.aiku.data.api.remote.GroupApi
import com.aiku.data.dto.IdDto
import com.aiku.data.dto.group.GroupDto
import com.aiku.data.dto.group.GroupOverviewPaginationDto
import com.aiku.data.dto.group.request.CreateGroupRequestDto
import com.aiku.data.dto.schedule.LocationDto
import com.aiku.data.dto.schedule.UserScheduleOverviewDto
import com.aiku.data.dto.schedule.UserScheduleOverviewPaginationDto
import com.squareup.moshi.Json
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

class GroupRemoteDataSource @Inject constructor(
    private val groupApi: GroupApi
) {

    suspend fun createGroup(request: CreateGroupRequestDto): IdDto {
        return groupApi.createGroup(request)
    }

    suspend fun deleteGroup(groupId: Long) {
        groupApi.deleteGroup(groupId)
    }

    suspend fun fetchGroups(page:Int): GroupOverviewPaginationDto {
        //TODO : return groupApi.fetchGroups(page)
        return GroupOverviewPaginationDto(
            page = page,
            data = List(11) { index ->
                // 임의의 더미 데이터 생성
                GroupOverviewPaginationDto.GroupOverviewDto(
                    id = index.toLong(),
                    name = "Dummy Group $index",
                    memberSize = Random.nextInt(1, 10), // 1부터 10 사이의 랜덤 멤버 수
                    lastScheduleTime = LocalDateTime.now().plusHours(Random.nextLong(-24, 24)),
                )
            }
        )
    }

    suspend fun fetchGroup(groupId: Long): GroupDto {
        return groupApi.fetchGroup(groupId)
    }

    suspend fun enterGroup(groupId: Long) {
        groupApi.enterGroup(groupId)
    }

    suspend fun exitGroup(groupId: Long) {
        groupApi.exitGroup(groupId)
    }
}