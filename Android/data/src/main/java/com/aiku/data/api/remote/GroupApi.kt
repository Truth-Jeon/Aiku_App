package com.aiku.data.api.remote

import com.aiku.data.dto.IdDto
import com.aiku.data.dto.group.GroupDto
import com.aiku.data.dto.group.GroupOverviewPaginationDto
import com.aiku.data.dto.group.request.CreateGroupRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {
    @POST("groups")
    suspend fun createGroup(@Body request: CreateGroupRequestDto): IdDto

    @DELETE("groups/{groupId}")
    suspend fun deleteGroup(@Path("groupId") groupId: Long)

    @GET("groups")
    suspend fun fetchGroups(@Query("page") page: Int,): GroupOverviewPaginationDto

    @GET("groups/{groupId}")
    suspend fun fetchGroup(@Path("groupId") groupId: Long): GroupDto

    @POST("groups/{groupId}/enter")
    suspend fun enterGroup(@Path("groupId") groupId: Long)

    @POST("groups/{groupId}/exit")
    suspend fun exitGroup(@Path("groupId") groupId: Long)
}