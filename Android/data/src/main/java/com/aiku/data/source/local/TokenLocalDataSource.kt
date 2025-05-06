package com.aiku.data.source.local

import com.aiku.data.api.local.TokenSharedPreferencesStorage
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
    private val tokenSharedPreferencesStorage: TokenSharedPreferencesStorage
) {

    suspend fun saveAccessToken(token: String) {
        tokenSharedPreferencesStorage.saveAccessToken(token)
    }

    suspend fun saveRefreshToken(token: String) {
        tokenSharedPreferencesStorage.saveRefreshToken(token)
    }

    suspend fun getAccessToken(): String? {
        return tokenSharedPreferencesStorage.getAccessToken()
    }

    suspend fun getRefreshToken(): String? {
        return tokenSharedPreferencesStorage.getRefreshToken()
    }

    suspend fun removeAccessToken() {
        tokenSharedPreferencesStorage.removeAccessToken()
    }

    suspend fun removeRefreshToken() {
        tokenSharedPreferencesStorage.removeRefreshToken()
    }
}