package com.aiku.aiku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.aiku.aiku.serializer.UserEntitySerializer
import com.aiku.core.qualifer.IoDispatcher
import com.aiku.data.UserEntity
import com.aiku.data.api.local.TokenSharedPreferencesStorage
import com.aiku.data.api.local.UserDataStoreStorage
import com.aiku.data.api.local.room.AikuDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

private val Context.userProtoDataStore: DataStore<UserEntity> by dataStore(fileName = "user.pb", serializer = UserEntitySerializer)

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): AikuDatabase {
        return Room.databaseBuilder(
            context,
            AikuDatabase::class.java, "aiku.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserEntity> {
        return context.userProtoDataStore
    }

    @Provides
    @Singleton
    fun provideUserDataStoreManager(
        dataStore: DataStore<UserEntity>
    ): UserDataStoreStorage {
        return UserDataStoreStorage(dataStore)
    }

    @Provides
    @Singleton
    fun provideTokenSharedPreferencesStorage(
        @ApplicationContext context: Context,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): TokenSharedPreferencesStorage {
        return TokenSharedPreferencesStorage(context, coroutineDispatcher)
    }
}