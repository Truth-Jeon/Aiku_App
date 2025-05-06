package com.aiku.domain.util

object RegexUtil {

    // TODO: 정규식 수정
    private const val NICKNAME_REGEX = "^[a-zA-Z0-9가-힣]{2,5}$"
    private const val PHONE_NUMBER_REGEX = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"

    fun isValidNickname(nickname: String): Boolean {
        return nickname.matches(Regex(NICKNAME_REGEX))
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex(PHONE_NUMBER_REGEX))
    }
}