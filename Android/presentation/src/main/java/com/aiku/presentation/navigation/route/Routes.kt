package com.aiku.presentation.navigation.route

import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.schedule.LocationState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.TermViewState
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
object Routes {

    @Serializable
    object Splash

    @Serializable
    object Auth {
        @Serializable
        object Graph

        @Serializable
        object Login

        @Serializable
        data class TermAgreement(val idToken: String, val email: String?)

        @Serializable
        data class TermContent(val identifier: Int)

        @Serializable
        data class ProfileEdit(val idToken: String, val email: String?)
    }

    object Main {
        @Serializable
        object Graph

        @Serializable
        object Home

        @Serializable
        object MySchedule

        @Serializable
        object MyPage

        @Serializable
        data class Group(val groupId: Long, val groupName: String)

        @Serializable
        object ScheduleRunning

        object CreateSchedule {
            @Serializable
            object Graph

            @Serializable
            object First
            // TODO : 약속 생성 Route 추가
        }

        @Serializable
        object Notification

        @Serializable
        object Shop

        @Serializable
        object NotificationSetting

        @Serializable
        object SeeTerms

        @Serializable
        data class SeeTermDetail(val term: TermViewState)

        @Serializable
        object Inquiry
    }

    object ScheduleWaiting {
        @Serializable
        object Graph

        @Serializable
        data class ScheduleWaiting(
            @Serializable val group: GroupState = GroupState(0, "", emptyList()),
            @Serializable val groupScheduleOverview: GroupScheduleOverviewState = GroupScheduleOverviewState(
                0, "", LocationState(.0, .0, ""),
                LocalDateTime.MAX,
                ScheduleStatus.WAIT, 0, false)
        )

        @Serializable
        data class Betting(
            @Serializable val group: GroupState,
            @Serializable val member: MemberState,
            val scheduleId: Long,
        )
    }
}