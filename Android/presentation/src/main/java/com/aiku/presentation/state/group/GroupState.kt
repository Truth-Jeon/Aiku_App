package com.aiku.presentation.state.group

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.group.Group

import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.toMemberState
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Immutable
data class GroupState(
    val id: Long,
    val name: String,
    val members: List<MemberState>,
) : Parcelable {

    fun toGroup(): Group = Group(
        id = id,
        name = name,
        members = members.map { it.toMember() }
    )
}

fun Group.toGroupState(): GroupState = GroupState(
    id = id,
    name = name,
    members = members.map { it.toMemberState() }
)