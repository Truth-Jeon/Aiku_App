package com.aiku.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.util.getParcelableCompat
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal object GroupNavType : NavType<GroupState>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): GroupState {
        return bundle.getParcelableCompat<GroupState>(key) as GroupState
    }

    override fun parseValue(value: String): GroupState {
        return Json.decodeFromString<GroupState>(value)
    }

    override fun put(bundle: Bundle, key: String, value: GroupState) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: GroupState): String {
        return Uri.encode(Json.encodeToString(value))
    }
}

internal object GroupScheduleOverviewNavType : NavType<GroupScheduleOverviewState>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): GroupScheduleOverviewState {
        return bundle.getParcelableCompat<GroupScheduleOverviewState>(key) as GroupScheduleOverviewState
    }

    override fun parseValue(value: String): GroupScheduleOverviewState {
        return Json.decodeFromString<GroupScheduleOverviewState>(value)
    }

    override fun put(bundle: Bundle, key: String, value: GroupScheduleOverviewState) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: GroupScheduleOverviewState): String {
        return Uri.encode(Json.encodeToString(value))
    }
}

internal object MemberNavType : NavType<MemberState>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): MemberState {
        return bundle.getParcelableCompat<MemberState>(key) as MemberState
    }

    override fun parseValue(value: String): MemberState {
        return Json.decodeFromString<MemberState>(value)
    }

    override fun put(bundle: Bundle, key: String, value: MemberState) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: MemberState): String {
        return Uri.encode(Json.encodeToString(value))
    }
}

internal object TermNavType : NavType<TermViewState>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): TermViewState {
        return bundle.getParcelableCompat<TermViewState>(key) as TermViewState
    }

    override fun parseValue(value: String): TermViewState {
        return Json.decodeFromString<TermViewState>(value)
    }

    override fun put(bundle: Bundle, key: String, value: TermViewState) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: TermViewState): String {
        return Uri.encode(Json.encodeToString(value))
    }
}
