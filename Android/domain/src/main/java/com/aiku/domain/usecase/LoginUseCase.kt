package com.aiku.domain.usecase

import com.aiku.domain.exception.ErrorResponse
import com.aiku.domain.exception.TOKEN_NOT_FOUND
import com.aiku.domain.model.token.Token
import com.aiku.domain.repository.TokenRepository
import com.aiku.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
){

    // 카카오톡으로 로그인
    private fun loginWithKakaoTalk(): Flow<Token> {
        return loginRepository.loginWithKakaoTalk()
    }

    // 카카오 계정으로 로그인
    private fun loginWithKakaoAccount(): Flow<Token> {
        return loginRepository.loginWithKakaoAccount()
    }

    // 로그인 방법 선택
    operator fun invoke(useKakaoTalk: Boolean): Flow<Token> {
        return if (useKakaoTalk) {
            loginWithKakaoTalk()
        } else {
            loginWithKakaoAccount()
        }
    }

    // 자동 로그인
    suspend fun autoLogin(): Flow<Token> {
        val refreshToken = tokenRepository.getRefreshToken()
        val accessToken = tokenRepository.getAccessToken()
        if (refreshToken != null && accessToken != null){
            return loginRepository.loginWithToken(refreshToken, accessToken)
        }else {
            throw ErrorResponse(TOKEN_NOT_FOUND, "저장된 토큰이 없습니다.")
        }
    }

    // Token
    suspend fun saveAccessToken(token: Token) {
        tokenRepository.saveAccessToken(token)
    }

    suspend fun saveRefreshToken(token: Token) {
        tokenRepository.saveRefreshToken(token)
    }

    suspend fun logout() {
        tokenRepository.removeAccessToken()
        tokenRepository.removeRefreshToken()
    }
}
