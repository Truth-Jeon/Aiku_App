package com.aiku.data.dto

import com.aiku.domain.model.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @see com.aiku.data.UserEntity
 */
@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "image") val image: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "phoneNumber") val phoneNumber: String?
) {
    fun User.toUserDto() = UserDto(
        image = image,
        name = nickname,
        phoneNumber = phoneNumber,
    )
}

