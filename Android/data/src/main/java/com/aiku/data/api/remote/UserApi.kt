package com.aiku.data.api.remote

import com.aiku.data.dto.user.TermsDto
import com.aiku.data.dto.user.UserDto
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun fetchUser(): UserDto

    @GET("term")
    suspend fun fetchTerms(): TermsDto
}