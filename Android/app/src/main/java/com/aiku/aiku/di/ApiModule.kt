package com.aiku.aiku.di

import com.aiku.core.qualifer.Auth
import com.aiku.core.qualifer.NoAuth
import com.aiku.data.api.remote.TokenApi
import com.aiku.data.api.remote.GroupApi
import com.aiku.data.api.remote.NoAuthTokenApi
import com.aiku.data.api.remote.ScheduleApi
import com.aiku.data.api.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideTokenApi(
        @Auth retrofit: Retrofit
    ): TokenApi {
        return retrofit.create(TokenApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoAuthTokenApi(@NoAuth retrofit: Retrofit): NoAuthTokenApi {
        return retrofit.create(NoAuthTokenApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGroupApi(@Auth retrofit: Retrofit): GroupApi {
        return retrofit.create(GroupApi::class.java)
    }
    @Provides
    @Singleton
    fun provideUserApi(@Auth retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    @Provides
    @Singleton
    fun provideScheduleApi(@Auth retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }
}