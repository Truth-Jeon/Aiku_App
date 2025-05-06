package com.aiku.presentation.state.user

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Badge
import com.aiku.domain.model.user.Profile
import com.aiku.domain.model.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class UserState(
    val id: Long,
    val nickname: String,
    val kakaoId: String,
    val profile: ProfileState,
    val badge: BadgeState,
    val point: Int,
): Parcelable {

    fun toUser(): User = User(
        id = id,
        nickname = nickname,
        kakaoId = kakaoId,
        profile = profile.toProfile(),
        badge = badge.toBadge(),
        point = point
    )

    companion object {
        val EMPTY = UserState(
            id = 0,
            nickname = "",
            kakaoId = "",
            profile = ProfileState(
                type = ProfileType.CHAR,
                image = "",
                character = ProfileCharacter.C01,
                background = ProfileBackground.GREEN
            ),
            badge = BadgeState(
                name = "",
                image = ""
            ),
            point = 0
        )
    }
}

fun User.toUserState(): UserState = UserState(
    id = id,
    nickname = nickname,
    kakaoId = kakaoId,
    profile = profile.toProfileState(),
    badge = badge.toBadgeState(),
    point = point
)