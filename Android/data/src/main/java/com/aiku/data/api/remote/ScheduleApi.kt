package com.aiku.data.api.remote

import com.aiku.data.dto.IdDto
import com.aiku.data.dto.schedule.GroupScheduleOverviewPaginationDto
import com.aiku.data.dto.schedule.ScheduleDto
import com.aiku.data.dto.schedule.request.BetAkuReqDto
import com.aiku.data.dto.schedule.UserScheduleOverviewPaginationDto
import com.aiku.data.dto.schedule.request.CreateScheduleReqDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

interface ScheduleApi {

    @POST("groups/{groupId}/schedules")
    suspend fun createSchedule(
        @Path("groupId") groupId: Long,
        @Body createScheduleRequest: CreateScheduleReqDto
    ): IdDto

    @GET("groups/{groupId}/schedules")
    suspend fun fetchGroupSchedules(
        @Path("groupId") groupId: Long,
        @Query("page") page: Int,
        @Query("startDate") startDate: LocalDateTime,
        @Query("endDate") endDate: LocalDateTime
    ): GroupScheduleOverviewPaginationDto

    @GET("groups/{groupId}/schedules/{scheduleId}")
    suspend fun fetchGroupScheduleDetail(
        @Path("groupId") groupId: Long,
        @Path("scheduleId") scheduleId: Long
    ): ScheduleDto

    @POST("schedules/{scheduleId}/bettings")
    suspend fun bet(
        @Path("scheduleId") scheduleId: Long,
        @Body betAkuRequest: BetAkuReqDto
    ): Unit

    @GET("/member/schedules")
    suspend fun fetchUserSchedules(
        @Query("page") page: Int,
        @Query("startDate") startDate: LocalDateTime,
        @Query("endDate") endDate: LocalDateTime
    ): UserScheduleOverviewPaginationDto

}