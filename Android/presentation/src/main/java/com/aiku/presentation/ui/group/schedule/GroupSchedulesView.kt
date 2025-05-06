package com.aiku.presentation.ui.group.schedule

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewPaginationState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.schedule.LocationState
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.ui.component.card.ScheduleCard
import com.aiku.presentation.ui.component.layout.EmptyContentView
import java.time.LocalDateTime

@Composable
fun GroupSchedulesView(
    modifier: Modifier,
    group: GroupState,
    scheduleOverviewPagination: GroupScheduleOverviewPaginationState,
    onScheduleCreateClicked: () -> Unit = {},
    onNavigateToWaitingScheduleScreen: (GroupScheduleOverviewState, GroupState) -> Unit = { _, _ -> }
) {

    var showParticipateDialog by remember { mutableStateOf(false) }
    if (scheduleOverviewPagination.groupScheduleOverview.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            item {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    Text(
                        text = stringResource(id = R.string.schedule_running),
                        style = Body2,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scheduleOverviewPagination.runningSchedule.toString(),
                        style = Body2,
                        fontWeight = FontWeight.Bold,
                        color = CobaltBlue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    VerticalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(id = R.string.schedule_waiting),
                        style = Body2,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scheduleOverviewPagination.waitingSchedule.toString(),
                        style = Body2,
                        fontWeight = FontWeight.Bold,
                        color = CobaltBlue,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(scheduleOverviewPagination.groupScheduleOverview.size) {
                ScheduleCard(
                    modifier = Modifier.padding(bottom = 12.dp),
                    schedule = scheduleOverviewPagination.groupScheduleOverview[it],
                    onClick = { status ->
                        when (status) {
                            ScheduleStatus.BEFORE_PARTICIPATION -> {
                                showParticipateDialog = true
                            }

                            ScheduleStatus.RUN -> Unit
                            ScheduleStatus.WAIT -> {
                                onNavigateToWaitingScheduleScreen(
                                    scheduleOverviewPagination.groupScheduleOverview[it],
                                    group
                                )
                            }

                            ScheduleStatus.TERM -> Unit
                            else -> Unit
                        }
                    }
                )
            }
        }
    } else {
        EmptyContentView(
            modifier = modifier,
            title = stringResource(id = R.string.create_schedule),
            onBottomChipClicked = onScheduleCreateClicked
        )
    }

    if (showParticipateDialog) {
        SelectParticipateOptionDialog(
            onEnterClicked = { showParticipateDialog = false },
            onDismissRequest = { showParticipateDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupScheduleViewPreview() {
    GroupSchedulesView(
        modifier = Modifier,
        group = GroupState(
            id = 1,
            name = "놀자팟",
            members = listOf()
        ),
        scheduleOverviewPagination = GroupScheduleOverviewPaginationState(
            page = 1,
            groupId = 1,
            runningSchedule = 2,
            waitingSchedule = 3,
            groupScheduleOverview = listOf(
                GroupScheduleOverviewState(
                    id = 1,
                    name = "즐건 놀자팟^^",
                    location = LocationState(
                        name = "홍대 입구역 1번 출구",
                        latitude = 37.123456,
                        longitude = 127.123456
                    ),
                    time = LocalDateTime.now(),
                    status = ScheduleStatus.RUN,
                    memberSize = 4,
                    accept = false
                ), GroupScheduleOverviewState(
                    id = 1,
                    name = "안즐건 공부팟ㅠㅠ",
                    location = LocationState(
                        name = "건대 7번 출구",
                        latitude = 37.123456,
                        longitude = 127.123456
                    ),
                    time = LocalDateTime.now(),
                    status = ScheduleStatus.WAIT,
                    memberSize = 4,
                    accept = false
                )
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyGroupScheduleViewPreview() {
    GroupSchedulesView(
        modifier = Modifier,
        group = GroupState(
            id = 1,
            name = "놀자팟",
            members = listOf()
        ),
        scheduleOverviewPagination = GroupScheduleOverviewPaginationState(
            page = 1,
            groupId = 1,
            runningSchedule = 0,
            waitingSchedule = 0,
            groupScheduleOverview = listOf()
        )
    )
}