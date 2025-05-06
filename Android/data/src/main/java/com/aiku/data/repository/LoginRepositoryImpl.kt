package com.aiku.data.repository

import com.aiku.data.source.remote.LoginRemoteDataSource
import com.aiku.domain.model.token.Token
import com.aiku.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : LoginRepository {

    override fun loginWithKakaoTalk(): Flow<Token> = flow {
        val tokenDto = loginRemoteDataSource.loginWithKakaoTalk()
        emit(tokenDto.toToken())
    }.catch { e ->
        throw e
    }.flowOn(coroutineDispatcher)

    override fun loginWithKakaoAccount(): Flow<Token> = flow {
        val tokenDto = loginRemoteDataSource.loginWithKakaoAccount()
        emit(tokenDto.toToken())
    }.catch { e ->
        throw e
    }.flowOn(coroutineDispatcher)

    override fun loginWithToken(refreshToken: String, accessToken: String): Flow<Token> = flow {
        val tokenDto = loginRemoteDataSource.loginWithToken(refreshToken, accessToken)
        emit(tokenDto.toToken())
    }.catch { e ->
        throw e
    }.flowOn(coroutineDispatcher)
}
