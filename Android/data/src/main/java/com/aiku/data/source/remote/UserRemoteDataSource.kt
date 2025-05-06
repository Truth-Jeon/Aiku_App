package com.aiku.data.source.remote

import com.aiku.data.api.remote.UserApi
import com.aiku.data.dto.user.BadgeDto
import com.aiku.data.dto.user.TermDto
import com.aiku.data.dto.user.TermsDto
import com.aiku.data.dto.user.UserDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun saveUser(user: UserDto) {
        // userApi.saveUser(user)
    }

    suspend fun fetchUser(): UserDto {
        return userApi.fetchUser()
    }

    suspend fun fetchTerms(): TermsDto {
        return userApi.fetchTerms()
    }
}