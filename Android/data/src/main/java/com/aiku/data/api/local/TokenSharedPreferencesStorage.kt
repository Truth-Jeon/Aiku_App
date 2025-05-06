package com.aiku.data.api.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenSharedPreferencesStorage @Inject constructor(
    private val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPref = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun saveAccessToken(
        accessToken: String,
    ) {
        withContext(coroutineDispatcher) {
            sharedPref.edit {
                putString(SHARED_PREF_KEY, accessToken)
            }
        }
    }

    suspend fun saveRefreshToken(
        refreshToken: String,
    ) {
        withContext(coroutineDispatcher) {
            sharedPref.edit {
                putString(SHARED_PREF_REFRESH_KEY, refreshToken)
            }
        }
    }

    suspend fun getAccessToken(): String? {
        return withContext(coroutineDispatcher) {
            sharedPref.getString(SHARED_PREF_KEY, null)
        }
    }

    suspend fun getRefreshToken(): String? {
        return withContext(coroutineDispatcher) {
            sharedPref.getString(SHARED_PREF_REFRESH_KEY, null)
        }
    }

    suspend fun removeAccessToken() {
        withContext(coroutineDispatcher) {
            sharedPref.edit {
                remove(SHARED_PREF_KEY)
            }
        }
    }

    suspend fun removeRefreshToken() {
        withContext(coroutineDispatcher) {
            sharedPref.edit {
                remove(SHARED_PREF_REFRESH_KEY)
            }
        }
    }

    companion object {
        private const val FILE_NAME = "encrypted_shared_prefs"

        private const val SHARED_PREF_KEY = "accessToken"
        private const val SHARED_PREF_REFRESH_KEY = "refreshToken"
    }
}