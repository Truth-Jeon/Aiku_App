package com.aiku.domain.model.user

data class Member(
    val id: Long,
    val nickname: String,
    val profile: Profile,
    val point: Long
)
