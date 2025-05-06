package com.aiku.domain.repository

import com.aiku.domain.model.token.Token
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun loginWithKakaoTalk(): Flow<Token>
    fun loginWithKakaoAccount(): Flow<Token>
    fun loginWithToken(refreshToken : String, accessToken : String): Flow<Token>
}