package com.aiku.data.dto.group

import com.aiku.domain.model.group.GroupOverviewPagination
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class GroupOverviewPaginationDto(
    @Json(name = "page") val page: Int?,
    @Json(name = "data") val data: List<GroupOverviewDto>?,
) {

    @JsonClass(generateAdapter = true)
    data class GroupOverviewDto(
        @Json(name = "groupId") val id: Long?,
        @Json(name = "groupName") val name: String?,
        @Json(name = "memberSize") val memberSize: Int?,
        @Json(name = "lastScheduleTime") val lastScheduleTime: LocalDateTime?,  // 2024-01-01T00:00:00
    ) {

        fun toGroupOverview(): GroupOverviewPagination.GroupOverview = GroupOverviewPagination.GroupOverview(
            id = id ?: 0,
            name = name ?: "",
            memberSize = memberSize ?: 0,
            lastScheduleTime = lastScheduleTime ?: LocalDateTime.MIN
        )
    }

    fun toGroupOverviewPagination(): GroupOverviewPagination = GroupOverviewPagination(
        page = page ?: 1,
        data = data?.map { it.toGroupOverview() } ?: emptyList()
    )
}