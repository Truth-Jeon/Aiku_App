package com.aiku.data.repository

import com.aiku.data.dto.user.UserDto
import com.aiku.data.dto.user.toTerms
import com.aiku.data.dto.user.toUserDto
import com.aiku.data.entity.toUser
import com.aiku.data.entity.toUserEntity
import com.aiku.data.source.local.UserLocalDataSource
import com.aiku.data.source.remote.UserRemoteDataSource
import com.aiku.domain.model.user.Terms
import com.aiku.domain.model.user.User
import com.aiku.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : UserRepository {

    private val user = flow {
        emit(userRemoteDataSource.fetchUser())
    }.map {
        it.toUser()
    }.stateIn(
        scope = CoroutineScope(coroutineDispatcher),
        started = SharingStarted.Eagerly,
        initialValue = UserDto.EMPTY.toUser()
    )

    override fun saveUser(user: User): Flow<Unit> {
        return flow {
            emit(userRemoteDataSource.saveUser(user.toUserDto()))
            emit(userLocalDataSource.saveUser(user.toUserEntity()))
        }.flowOn(coroutineDispatcher)
    }

    override fun fetchUser(): Flow<User> {
        return user
    }

    override suspend fun fetchTerms(): Terms {
        return userRemoteDataSource.fetchTerms().toTerms()
    }
}
