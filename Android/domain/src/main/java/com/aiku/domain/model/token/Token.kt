package com.aiku.domain.model.token


data class Token(
    val grantType: String?,
    val accessToken: String?,
    val refreshToken: String?
)