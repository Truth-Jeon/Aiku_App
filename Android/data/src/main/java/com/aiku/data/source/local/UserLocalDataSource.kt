package com.aiku.data.source.local

import com.aiku.data.UserEntity
import com.aiku.data.api.local.UserDataStoreStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dataStoreManager: UserDataStoreStorage
) {

    suspend fun saveUser(user: UserEntity) {
        dataStoreManager.saveUser(user)
    }
}
