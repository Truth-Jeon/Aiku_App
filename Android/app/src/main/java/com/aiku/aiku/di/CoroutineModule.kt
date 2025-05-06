package com.aiku.aiku.di

import com.aiku.core.qualifer.DefaultDispatcher
import com.aiku.core.qualifer.IoDispatcher
import com.aiku.core.qualifer.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @DefaultDispatcher
    @Provides
    @Singleton
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    @Singleton
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideDefaultCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ) = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Provides
    @Singleton
    fun provideIoCoroutineScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = CoroutineScope(SupervisorJob() + ioDispatcher)

    @Provides
    @Singleton
    fun provideMainCoroutineScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ) = CoroutineScope(SupervisorJob() + mainDispatcher)
}