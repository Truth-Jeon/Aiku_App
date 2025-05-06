package com.aiku.aiku.di

import com.aiku.aiku.BuildConfig
import com.aiku.core.adapter.LocalDateTimeAdapter
import com.aiku.core.qualifer.Auth
import com.aiku.core.qualifer.AuthHeaderInterceptor
import com.aiku.core.qualifer.BaseUrl
import com.aiku.core.qualifer.NoAuth
import com.aiku.core.qualifer.ResponseInterceptor
import com.aiku.data.source.local.TokenLocalDataSource
import com.aiku.domain.exception.ClientNetworkException
import com.aiku.domain.exception.ErrorResponse
import com.aiku.domain.exception.ServerNetworkException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseUrl() : String {
        return "http://3.36.49.12:8080/"
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        tokenLocalDataSource: TokenLocalDataSource,
    ): Authenticator {
        return Authenticator { _, response ->
            runBlocking {
                val refreshToken = tokenLocalDataSource.getRefreshToken()
                if (refreshToken == null) {
                    response.close()
                    return@runBlocking null
                }

                response.request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $refreshToken")
                    .build()
            }
        }
    }

    @AuthHeaderInterceptor
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenLocalDataSource: TokenLocalDataSource,
    ): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val accessToken = runBlocking { tokenLocalDataSource.getAccessToken() ?: "" }
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(newRequest)
        }
    }

    @ResponseInterceptor
    @Provides
    @Singleton
    fun provideResponseInterceptor(
        moshi: Moshi
    ) : Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            when (response.code) {
                in 400 ..< 500 -> throw ClientNetworkException(response.code)
                in 500 ..< 600 -> throw ServerNetworkException(response.code)
            }
            val originalBody = response.body ?: throw UnknownError("response body is null")

            val source = originalBody.source()
            val buffer = source.buffer.clone()
            val bodyString = buffer.readUtf8()

            if (response.isSuccessful.not()) {
                val exception = moshi.adapter(ErrorResponse::class.java).fromJson(bodyString)
                throw exception ?: UnknownError("error response is null")
            }

            val jsonObject = JSONObject(bodyString)
            val resultObject = jsonObject.getJSONObject("result")
            val newResponseBody = resultObject.toString().toResponseBody(originalBody.contentType())

            response.newBuilder()
                .body(newResponseBody)
                .build()
        }
    }

    @Auth
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @AuthHeaderInterceptor authHeaderInterceptor: Interceptor,
        @ResponseInterceptor responseInterceptor: Interceptor,
        authenticator: Authenticator
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authHeaderInterceptor)
            .addInterceptor(responseInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(
        @ResponseInterceptor responseInterceptor: Interceptor,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(responseInterceptor)
            .build()
    }

    @Auth
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @BaseUrl baseUrl: String,
        @Auth okHttpClient: OkHttpClient,
        moshi: Moshi
    ) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthRetrofit(
        @BaseUrl baseUrl: String,
        @NoAuth okHttpClient: OkHttpClient,
        moshi: Moshi
    ) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideMoshi() : Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateTimeAdapter())
            .build()
    }
}