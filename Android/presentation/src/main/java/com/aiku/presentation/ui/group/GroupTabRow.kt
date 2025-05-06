package com.aiku.presentation.ui.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Subtitle_4G
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green4
import com.aiku.presentation.ui.group.schedule.GroupSchedulesView
import com.aiku.presentation.ui.group.viewmodel.GroupUiState
import com.aiku.presentation.ui.group.viewmodel.ScheduleOverviewUiState
import com.aiku.presentation.ui.type.GroupTabType
import kotlinx.coroutines.launch

@Composable
fun GroupTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    groupUiState: GroupUiState,
    scheduleOverviewUiState: ScheduleOverviewUiState,
    onNavigateToWaitingScheduleScreen: (GroupScheduleOverviewState, GroupState) -> Unit = { _, _ -> }
) {

    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val tabs = GroupTabType.entries

    Column(
        modifier = modifier
    ) {
        TabRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    height = 4.dp,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Green4
                )
            }
        ) {

            tabs.forEachIndexed { index, tab ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }) {
                    Text(
                        modifier = Modifier.padding(vertical = 13.dp),
                        text = stringResource(tab.titleResId),
                        style = Subtitle_4G.copy(
                            color = if (pagerState.currentPage == index) Color.Black else Gray03
                        )
                    )
                }
            }

        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            state = pagerState
        ) { index ->
            if (index == tabs.indexOf(GroupTabType.MEMBER)) {
                when (groupUiState) {
                    is GroupUiState.Success -> {
                        val groupState = groupUiState.group
                        GroupMembersView(
                            modifier = Modifier.fillMaxSize(),
                            scrollState = scrollState,
                            members = groupState.members,
                            onInviteClicked = {}    // TODO 초대 클릭
                        )
                    }

                    is GroupUiState.Loading -> {
                        // TODO 로딩뷰
                    }

                    is GroupUiState.Error -> {
                        // TODO 에러뷰
                    }
                }

            } else if (index == tabs.indexOf(GroupTabType.SCHEDULE)) {
                // TODO 약속뷰
                when (scheduleOverviewUiState) {
                    is ScheduleOverviewUiState.Loading -> {
                        // TODO 로딩뷰
                    }

                    is ScheduleOverviewUiState.Success -> {
                        GroupSchedulesView(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            group = (groupUiState as GroupUiState.Success).group,
                            scheduleOverviewPagination = scheduleOverviewUiState.scheduleOverviewPagination,
                            onScheduleCreateClicked = {},    // TODO 약속 생성 클릭
                            onNavigateToWaitingScheduleScreen = onNavigateToWaitingScheduleScreen
                        )
                    }
                }
            }
        }
    }
}
