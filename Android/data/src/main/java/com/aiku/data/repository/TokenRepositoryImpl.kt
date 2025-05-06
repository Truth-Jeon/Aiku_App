package com.aiku.data.repository

import com.aiku.data.source.local.TokenLocalDataSource
import com.aiku.domain.model.token.Token
import com.aiku.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override suspend fun saveAccessToken(token: Token) {
        token.accessToken?.let { tokenLocalDataSource.saveAccessToken(it) }
    }

    override suspend fun saveRefreshToken(token: Token) {
        token.refreshToken?.let { tokenLocalDataSource.saveRefreshToken(it) }
    }

    override suspend fun getAccessToken(): String? {
        return tokenLocalDataSource.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenLocalDataSource.getRefreshToken()
    }

    override suspend fun removeAccessToken() {
       tokenLocalDataSource.removeAccessToken()
    }

    override suspend fun removeRefreshToken() {
        tokenLocalDataSource.removeRefreshToken()
    }

}