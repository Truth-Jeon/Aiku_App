package com.aiku.presentation.ui.screen.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Headline_3G
import com.aiku.core.theme.Subtitle_2G
import com.aiku.core.theme.Subtitle_4G
import com.aiku.presentation.navigation.route.Routes
import com.aiku.presentation.state.group.GroupOverviewState
import com.aiku.presentation.state.schedule.UserScheduleOverviewState
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Purple5
import com.aiku.presentation.theme.ScreenHorizontalPadding
import com.aiku.presentation.theme.Typo
import com.aiku.presentation.theme.Yellow5
import com.aiku.presentation.ui.component.button.FloatingActionPlusButton
import com.aiku.presentation.ui.screen.home.viewmodel.GroupsUiState
import com.aiku.presentation.ui.screen.home.viewmodel.HomeViewModel
import com.aiku.presentation.ui.screen.home.viewmodel.TodayUserSchedulesUiState
import com.aiku.presentation.util.noRippleClickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGroupClicked: (groupId: Long, groupName: String) -> Unit,
    onTodayScheduleClicked: () -> Unit,
    onNavigateToNotification: () -> Unit = {},
) {
    var showCreateGroupDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar( //TODO : Topbar 수정
                title = {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "AiKU",
                        style = Headline_3G,
                        color = CobaltBlue
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.noRippleClickable {
                            onNavigateToNotification()
                        },
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = stringResource(R.string.notification)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        modifier = Modifier.padding(end = 22.dp),
                        painter = painterResource(R.drawable.ic_store),
                        contentDescription = stringResource(R.string.store)
                    )
                }
            )
        },
        floatingActionButton = { FloatingActionPlusButton(onClick = { showCreateGroupDialog = true }) }
    ) { innerPadding ->

        HomeContent(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            onGroupClicked = onGroupClicked,
            onTodayScheduleClicked = onTodayScheduleClicked
        )

        if (showCreateGroupDialog) {
            CreateGroupDialog(
                onDismissRequest = { showCreateGroupDialog = false }
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onGroupClicked: (groupId: Long, groupName: String) -> Unit,
    onTodayScheduleClicked: () -> Unit
) {

    val todayUserSchedulesUiState by homeViewModel.todayUserSchedulesUiState.collectAsState()
    val lazyUserSchedulePagingItems = homeViewModel.userSchedules.collectAsLazyPagingItems()

    val groupsUiState by homeViewModel.groupsUiState.collectAsState()
    val lazyGroupPagingItems = homeViewModel.groups.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = ScreenHorizontalPadding)
        ) {
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                style = Subtitle_2G,
                color = Typo
            )
            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 17.dp),
                text = stringResource(id = R.string.home_today_schedule),
                style = Body2,
                color = Typo
            )

            when (todayUserSchedulesUiState) {
                TodayUserSchedulesUiState.Loading -> { }

                TodayUserSchedulesUiState.Error -> { }

                TodayUserSchedulesUiState.Success -> {
                    if (lazyUserSchedulePagingItems.itemCount == 0) {
                        EmptyTodayUserSchedule()
                    } else {
                        TodayUserSchedules(
                            lazyUserSchedulePagingItems,
                            onTodayScheduleClicked
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ScreenHorizontalPadding)
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Gray02,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                modifier = Modifier.padding(top = 26.dp, bottom = 19.dp),
                text = "닉네임's Group", //TODO : user이름으로 변경
                style = Subtitle_4G,
                color = Typo
            )
            when (groupsUiState) {
                GroupsUiState.Loading -> { }

                GroupsUiState.Error -> { }

                GroupsUiState.Success -> {
                    if (lazyUserSchedulePagingItems.itemCount == 0) {
                        //TODO : 그룹 empty
                    } else {
                        Groups(
                            lazyGroupPagingItems,
                            onGroupClicked)
                    }
                }
            }

        }

    }
}

@Composable
fun TodayUserSchedules(
    lazyPagingItems: LazyPagingItems<UserScheduleOverviewState>,
    onTodayScheduleClicked: () -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let {
                TodayUserScheduleCard(
                    schedule = it,
                    onClick = { onTodayScheduleClicked() }
                )
            }
        }


        /* 처리 중 상태 반영
        lazyPagingItems.apply {
            when {
                loadState.refresh is androidx.paging.LoadState.Loading -> {
                    item { Text("Refreshing schedules...") }
                }

                loadState.append is androidx.paging.LoadState.Loading -> {
                    item { Text("Loading more...") }
                }

                loadState.append is androidx.paging.LoadState.Error -> {
                    item { Text("Failed to load more schedules.") }
                }
            }
        }*/
    }
}

@Composable
fun Groups(
    lazyPagingItems: LazyPagingItems<GroupOverviewState>,
    onGroupClicked: (groupId: Long, groupName: String) -> Unit
) {
    val colors = listOf(Green5, Yellow5, Purple5)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let {
                val color = colors[index % colors.size]

                lazyPagingItems[index]?.let { it1 ->
                    GroupCard(
                        color = color,
                        onClick = { onGroupClicked(1, "야호") },
                        group = it1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }


        /* 처리 중 상태 반영
        lazyPagingItems.apply {
            when {
                loadState.refresh is androidx.paging.LoadState.Loading -> {
                    item { Text("Refreshing schedules...") }
                }

                loadState.append is androidx.paging.LoadState.Loading -> {
                    item { Text("Loading more...") }
                }

                loadState.append is androidx.paging.LoadState.Error -> {
                    item { Text("Failed to load more schedules.") }
                }
            }
        }*/
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    HomeScreen(
        onGroupClicked = { groupId, groupName ->
            navController.navigate(
                Routes.Main.Group(groupId, groupName)
            )
        },
        onTodayScheduleClicked = {}
    )
}