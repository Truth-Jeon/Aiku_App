package com.aiku.data.api.remote

import com.aiku.data.dto.TokenDto
import com.aiku.data.dto.group.request.IssueATRTRequest
import com.aiku.data.dto.group.request.IssueATRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("login/refresh")
    suspend fun issueAT(@Body request: IssueATRequest): TokenDto //TODO : Body 수정
}

interface NoAuthTokenApi {
    @POST("login/sign-in")
    suspend fun issueATRT(@Body request: IssueATRTRequest): TokenDto
}