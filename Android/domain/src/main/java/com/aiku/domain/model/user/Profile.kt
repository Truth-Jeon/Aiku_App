package com.aiku.domain.model.user

import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType

data class Profile(
    val type: ProfileType,
    val image: String,
    val character: ProfileCharacter,
    val background: ProfileBackground
)