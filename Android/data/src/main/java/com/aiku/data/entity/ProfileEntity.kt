package com.aiku.data.entity

import com.aiku.data.UserEntity
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Profile
import java.util.Locale

/**
 * @see com.aiku.data.UserEntity
 */
fun Profile.toProfileEntity(): UserEntity.ProfileEntity {
    return UserEntity.ProfileEntity.getDefaultInstance().toBuilder()
        .setProfileType(type.name)
        .setProfileImg(image)
        .setProfileCharacter(character.name)
        .setProfileBackground(background.name)
        .build()
}

fun UserEntity.ProfileEntity.toProfile(): Profile {
    return Profile(
        type = ProfileType.valueOf(profileType.uppercase(Locale.ROOT)),
        image = profileImg,
        character = ProfileCharacter.valueOf(profileCharacter.uppercase(Locale.ROOT)),
        background = ProfileBackground.valueOf(profileBackground.uppercase(Locale.ROOT))
    )
}