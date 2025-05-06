package com.aiku.presentation.state.user

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.user.Badge
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class BadgeState(
    val name: String,
    val image: String,
) : Parcelable {

    fun toBadge(): Badge = Badge(
        name = name,
        image = image
    )
}

fun Badge.toBadgeState(): BadgeState = BadgeState(
    name = name,
    image = image
)