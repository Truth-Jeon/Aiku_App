package com.aiku.aiku.di

import com.aiku.core.qualifer.IoDispatcher
import com.aiku.data.repository.GroupRepositoryImpl
import com.aiku.data.repository.NotificationRepositoryImpl
import com.aiku.data.repository.TermsRepositoryImpl
import com.aiku.data.repository.ScheduleRepositoryImpl
import com.aiku.data.repository.TokenRepositoryImpl
import com.aiku.data.repository.UserRepositoryImpl
import com.aiku.data.source.local.NotificationLocalDataSource
import com.aiku.data.source.local.TermsLocalDataSource
import com.aiku.data.source.local.TokenLocalDataSource
import com.aiku.data.source.local.UserLocalDataSource
import com.aiku.data.source.remote.GroupRemoteDataSource
import com.aiku.data.source.remote.ScheduleRemoteDataSource
import com.aiku.data.source.remote.UserRemoteDataSource
import com.aiku.domain.repository.GroupRepository
import com.aiku.domain.repository.NotificationRepository
import com.aiku.domain.repository.TermsRepository
import com.aiku.domain.repository.TokenRepository
import com.aiku.domain.repository.ScheduleRepository
import com.aiku.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        tokenLocalDataSource: TokenLocalDataSource
    ): TokenRepository {
        return TokenRepositoryImpl(tokenLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(userLocalDataSource, userRemoteDataSource, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideTermsRepository(
        termsLocalDataSource: TermsLocalDataSource
    ): TermsRepository {
        return TermsRepositoryImpl(termsLocalDataSource = termsLocalDataSource)
    }


    @Provides
    @Singleton
    fun provideGroupRepository(
        groupRemoteDataSource: GroupRemoteDataSource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GroupRepository {
        return GroupRepositoryImpl(groupRemoteDataSource = groupRemoteDataSource, coroutineDispatcher)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(
        scheduleRemoteDataSource: ScheduleRemoteDataSource
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(scheduleRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(
        notificationLocalDataSource: NotificationLocalDataSource
    ): NotificationRepository {
        return NotificationRepositoryImpl(notificationLocalDataSource)
    }
}