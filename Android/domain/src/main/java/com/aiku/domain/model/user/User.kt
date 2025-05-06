package com.aiku.domain.model.user

import com.aiku.domain.util.RegexUtil

data class User(
    val id: Long,
    val nickname: String,
    val kakaoId: String,
    val profile: Profile,
    val badge: Badge,
    val point: Int,
) {
    fun isNicknameEmpty() = nickname.isEmpty()
    fun isNicknameLengthExceed(max: Int) = nickname.length > max
    fun isInvalidNicknameFormat() = RegexUtil.isValidNickname(nickname).not()
}