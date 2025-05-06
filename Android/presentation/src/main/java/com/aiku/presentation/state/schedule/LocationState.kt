package com.aiku.presentation.state.schedule

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.schedule.Location
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Immutable
data class LocationState(
    val latitude: Double,
    val longitude: Double,
    val name: String
) : Parcelable

fun Location.toLocationState() = LocationState(latitude, longitude, name)