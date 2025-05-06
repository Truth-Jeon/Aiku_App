package com.aiku.domain.repository

import com.aiku.domain.model.user.Terms
import com.aiku.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun saveUser(user: User): Flow<Unit>
    fun fetchUser(): Flow<User>

    suspend fun fetchTerms(): Terms
}