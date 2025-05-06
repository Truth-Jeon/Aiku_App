package com.aiku.presentation.ui.group.schedule.waiting.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Headline_2G
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.schedule.LocationState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.graph.CircularTimeGraph
import com.aiku.presentation.ui.component.row.TimeIndicationRow
import com.aiku.presentation.ui.group.GroupTopAppBar
import com.aiku.presentation.ui.group.schedule.waiting.viewmodel.ScheduleUiState
import com.aiku.presentation.ui.group.schedule.waiting.viewmodel.WaitingScheduleViewModel
import com.aiku.presentation.util.formatSecondsToHHMMSS
import com.aiku.presentation.util.getSecondsDifferenceFromNow
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WaitingScheduleScreen(
    modifier: Modifier = Modifier,
    group: GroupState,
    scheduleOverview: GroupScheduleOverviewState,
    selectedMemberId: Long = 0,
    onMemberClicked: (MemberState) -> Unit = { },
    viewModel: WaitingScheduleViewModel = hiltViewModel(
        creationCallback = { factory: WaitingScheduleViewModel.Factory ->
            factory.create(group, scheduleOverview)
        }
    )
) {
    var secondsDiffFromNow by remember { mutableLongStateOf(scheduleOverview.time.getSecondsDifferenceFromNow()) }
    val scheduleUiState by viewModel.scheduleUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            GroupTopAppBar(title = group.name, actions = {
                IconButton(onClick = { /* BACK */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_exit),
                        contentDescription = stringResource(
                            id = R.string.group_more_icon_content_description
                        )
                    )
                }
            })
        }
    ) { innerPadding ->
        when (scheduleUiState) {
            is ScheduleUiState.Loading -> {

            }
            is ScheduleUiState.Error -> {

            }
            is ScheduleUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                shape = RoundedCornerShape(50),
                                elevation = CardDefaults.elevatedCardElevation(
                                    defaultElevation = 1.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .padding(vertical = 12.dp, horizontal = 30.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_calendar),
                                        contentDescription = stringResource(
                                            id = R.string.schedule_calendar_content_description
                                        ), tint = CobaltBlue
                                    )
                                    Column(
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        TimeIndicationRow(
                                            time = scheduleOverview.time,
                                            textStyle = Body2.copy(fontWeight = FontWeight.Medium),
                                        )
                                        Text(
                                            text = scheduleOverview.location.name,
                                            modifier = Modifier.padding(top = 2.dp),
                                            color = Color.Black,
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(36.dp))
                            Box(
                                modifier = Modifier.size(250.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                var totalTime = Duration.between((scheduleUiState as ScheduleUiState.Success).schedule.createdAt, scheduleOverview.time).seconds
                                var elapsedTime = Duration.between((scheduleUiState as ScheduleUiState.Success).schedule.createdAt, LocalDateTime.now(ZoneId.of("Asia/Seoul"))).seconds.coerceAtMost(totalTime)
                                CircularTimeGraph(
                                    modifier = Modifier.fillMaxSize(),
                                    totalTime = totalTime,
                                    elapsedTime = elapsedTime,
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.remaining_time),
                                        style = Body2,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp),
                                        text = secondsDiffFromNow.formatSecondsToHHMMSS(),
                                        style = Headline_2G,
                                    )
                                }
                            }

                            Text(
                                text = stringResource(R.string.participated_members),
                                style = Body2,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(top = 46.dp, start = 20.dp)
                                    .align(Alignment.Start)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(
                        count = ((scheduleUiState as ScheduleUiState.Success).schedule.members.size / 2f).roundToInt()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            ParticipantCard(
                                modifier = Modifier.weight(1f),
                                member = (scheduleUiState as ScheduleUiState.Success).schedule.members[2 * it],
                                rank = 2 * it + 1,
                                border = if (selectedMemberId == 2L * it + 1) BorderStroke(2.dp, Green5) else null
                            ) { member ->
                                onMemberClicked(member)
                            }
                            if (2 * it + 1 < (scheduleUiState as ScheduleUiState.Success).schedule.members.size) {
                                ParticipantCard(
                                    modifier = Modifier.weight(1f),
                                    member = (scheduleUiState as ScheduleUiState.Success).schedule.members[2 * it + 1],
                                    rank = 2 * it + 2,
                                    border = if (selectedMemberId == 2L * it + 2) BorderStroke(2.dp, Green5) else null
                                ) { member ->
                                    onMemberClicked(member)
                                }
                            }
                            else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        while(true) {
            secondsDiffFromNow = scheduleOverview.time.getSecondsDifferenceFromNow()
            delay(1000L)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WaitingScheduleScreenPreview() {
    WaitingScheduleScreen(
        modifier = Modifier,
        group = GroupState(
            id = 1,
            name = "놀자팟",
            members = listOf(),
        ),
        scheduleOverview = GroupScheduleOverviewState(
            id = 1,
            time = LocalDateTime.now(),
            status = ScheduleStatus.WAIT,
            accept = true,
            memberSize = 0,
            name = "한강 번개",
            location = LocationState(
                latitude = 0.0,
                longitude = 0.0,
                name = "한강어딘가"
            )
        )
    )
}