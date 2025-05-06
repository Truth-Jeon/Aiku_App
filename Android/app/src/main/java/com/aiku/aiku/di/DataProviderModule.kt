package com.aiku.aiku.di

import com.aiku.core.qualifer.IoDispatcher
import com.aiku.domain.repository.UserRepository
import com.aiku.presentation.base.UserDataProvider
import com.aiku.presentation.base.UserDataProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataProviderModule {

    @Provides
    @Singleton
    fun provideUserDataProvider(
        userRepository: UserRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): UserDataProvider {
        return UserDataProviderImpl(userRepository, CoroutineScope(SupervisorJob() + dispatcher))
    }
}