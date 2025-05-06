package com.aiku.presentation.state.user

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.user.Member
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.domain.model.user.Profile
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Immutable
data class MemberState(
    val id: Long,
    val nickname: String,
    val profile: ProfileState,
    val point: Long
) : Parcelable {

    fun toMember(): Member = Member(
        id = id,
        nickname = nickname,
        profile = profile.toProfile(),
        point = point
    )
}

fun Member.toMemberState(): MemberState = MemberState(
    id = id,
    nickname = nickname,
    profile = profile.toProfileState(),
    point = point
)
