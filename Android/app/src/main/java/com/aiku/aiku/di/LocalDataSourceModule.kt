package com.aiku.aiku.di

import com.aiku.data.api.local.TokenSharedPreferencesStorage
import com.aiku.data.api.local.UserDataStoreStorage
import com.aiku.data.source.local.TokenLocalDataSource
import android.content.Context
import com.aiku.data.api.local.room.AikuDatabase
import com.aiku.data.source.local.NotificationLocalDataSource
import com.aiku.data.source.local.TermsLocalDataSource
import com.aiku.data.source.local.UserLocalDataSource
import com.aiku.data.source.remote.LoginRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        userDataStoreManager: UserDataStoreStorage
    ): UserLocalDataSource {
        return UserLocalDataSource(userDataStoreManager)
    }

    @Provides
    @Singleton
    fun provideTokenLocalDataSource(
        tokenSharedPreferencesStorage: TokenSharedPreferencesStorage
    ): TokenLocalDataSource {
        return TokenLocalDataSource(tokenSharedPreferencesStorage)
    }

    @Provides
    @Singleton
    fun provideTermsLocalDataSource(@ApplicationContext context: Context): TermsLocalDataSource {
        return TermsLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideNotificationLocalDataSource(
        aikuDatabase: AikuDatabase
    ): NotificationLocalDataSource {
        return NotificationLocalDataSource(aikuDatabase)
    }
}
