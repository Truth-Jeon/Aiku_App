package com.aiku.data.dto.user

import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Profile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale

@JsonClass(generateAdapter = true)
data class ProfileDto(
    @Json(name = "profileType") val type: String?,  // IMG or CHAR
    @Json(name = "profileImg") val image: String?,
    @Json(name = "profileCharacter") val character: String?, // C01, C02, C03, C04
    @Json(name = "profileBackground") val background: String? // RED, GREEN, BLUE
) {

    fun toProfile(): Profile = Profile(
        type = type?.let { ProfileType.valueOf(it.uppercase(Locale.ROOT)) } ?: ProfileType.IMG,
        image = image ?: "",
        character = character?.let { ProfileCharacter.valueOf(it.uppercase(Locale.ROOT)) }
            ?: ProfileCharacter.NONE,
        background = background?.let { ProfileBackground.valueOf(it.uppercase(Locale.ROOT)) }
            ?: ProfileBackground.GRAY
    )

    companion object {
        val EMPTY = ProfileDto(
            type = ProfileType.CHAR.name,
            image = "",
            character = ProfileCharacter.C01.name,
            background = ProfileBackground.GREEN.name
        )
    }
}

fun Profile.toProfileDto() = ProfileDto(
    type = type.name,
    image = image,
    character = character.name,
    background = background.name
)