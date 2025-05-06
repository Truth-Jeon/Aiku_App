package com.aiku.data.source.remote

import android.content.Context
import android.util.Log
import com.aiku.data.api.remote.NoAuthTokenApi
import com.aiku.data.api.remote.TokenApi
import com.aiku.data.dto.TokenDto
import com.aiku.data.dto.group.request.IssueATRTRequest
import com.aiku.data.dto.group.request.IssueATRequest
import com.aiku.domain.exception.ERROR_AUTO_LOGIN
import com.aiku.domain.exception.ERROR_KAKAO_OIDC
import com.aiku.domain.exception.ERROR_KAKAO_SERVER
import com.aiku.domain.exception.ERROR_KAKAO_USER_EMAIL
import com.aiku.domain.exception.ERROR_SERVER_ISSUE_ATRT
import com.aiku.domain.exception.ErrorResponse
import com.aiku.domain.exception.TokenIssueErrorResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginRemoteDataSource @Inject constructor(
    @ActivityContext private val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val tokenApi: TokenApi,
    private val noAuthTokenApi: NoAuthTokenApi
) {

    suspend fun loginWithKakaoTalk(): TokenDto {
        return withContext(coroutineDispatcher) {
            try {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    performLogin { callback -> UserApiClient.instance.loginWithKakaoTalk(context, callback = callback) }
                } else {
                    loginWithKakaoAccount()
                }
            } catch (e: ErrorResponse) {
                throw e
            }
        }
    }

    suspend fun loginWithKakaoAccount(): TokenDto {
        return withContext(coroutineDispatcher) {
            try { performLogin { callback -> UserApiClient.instance.loginWithKakaoAccount(context, callback = callback) }
            } catch (e: ErrorResponse) { throw e }
        }
    }

    private suspend fun performLogin(
        loginAction: (callback: (token: OAuthToken?, error: Throwable?) -> Unit) -> Unit
    ): TokenDto {
        return try {
            // Kakao 로그인
            val tokenResult = suspendCoroutine<OAuthToken?> { continuation ->
                loginAction { token, error ->
                    if (error != null) {
                        continuation.resumeWith(Result.failure(error))
                    } else {
                        continuation.resumeWith(Result.success(token))
                    }
                }
            }


            val idToken = tokenResult?.idToken ?: throw ErrorResponse(ERROR_KAKAO_OIDC, "idToken 발급 실패")
            // val email = getUserEmail() //TODO

            try {
                // idToken -> AT, RT 발급
                noAuthTokenApi.issueATRT(request = IssueATRTRequest(idToken))
            } catch (e: Exception) {
                throw TokenIssueErrorResponse(ERROR_SERVER_ISSUE_ATRT, "Token issuance failed: ${e.message}", idToken = idToken, email = "email")
            }

        } catch (e: TokenIssueErrorResponse) {
            throw e
        } catch (e: Exception) {
            if (e is ErrorResponse && e.code == ERROR_KAKAO_OIDC) {
                throw ErrorResponse(ERROR_KAKAO_OIDC, "Kakao login failed: ${e.message}")
            } else {
                throw ErrorResponse(ERROR_KAKAO_SERVER, "An error occurred: ${e.message}")
            }
        }
    }


    //RT -> AT 재발급
    suspend fun loginWithToken(refreshToken: String, accessToken: String): TokenDto {
        return withContext(coroutineDispatcher) {
            try {
                tokenApi.issueAT(IssueATRequest(refreshToken, accessToken))
            } catch (e: Exception) { throw ErrorResponse(ERROR_AUTO_LOGIN, "Failed to refresh token: ${e.message}") }
        }
    }

    // 이메일 정보 가져오기
    suspend fun getUserEmail(): String = withContext(coroutineDispatcher) {
        suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                when {
                    error != null -> continuation.resumeWithException(ErrorResponse(ERROR_KAKAO_USER_EMAIL, "Failed to fetch user email: ${error.message}"))
                    user?.kakaoAccount?.email != null -> continuation.resume(user.kakaoAccount?.email!!)
                    else -> continuation.resumeWithException(ErrorResponse( ERROR_KAKAO_USER_EMAIL, "No email provided"))
                }
            }
        }
    }
}

