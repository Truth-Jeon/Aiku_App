package com.aiku.presentation.ui.group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.aiku.core.R
import com.aiku.core.theme.Subtitle3
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.ui.component.button.IconFloatingActionButton
import com.aiku.presentation.ui.component.dialog.DefaultAlertDialog
import com.aiku.presentation.ui.component.dialog.SimpleMenu
import com.aiku.presentation.ui.component.dialog.SimpleMenuDialog
import com.aiku.presentation.ui.group.viewmodel.GroupUiState
import com.aiku.presentation.ui.group.viewmodel.GroupViewModel
import com.aiku.presentation.ui.type.GroupTabType

enum class GroupSettingDialogRoute {
    Menu, ExitAlert
}

@Composable
fun GroupScreen(
    modifier: Modifier = Modifier,
    groupId: Long,
    groupName: String,
    onNavigateToWaitingScheduleScreen: (GroupScheduleOverviewState, GroupState) -> Unit = { _, _ -> },
    onNavigateToCreateScheduleScreen: (GroupState) -> Unit = { _ -> },
    viewModel: GroupViewModel = hiltViewModel(
        creationCallback = { factory: GroupViewModel.Factory ->
            factory.create(groupId)
        }
    )
) {
    val pagerState = rememberPagerState { GroupTabType.entries.size }
    val dialogNavController = rememberNavController()
    var showDialogNav by remember { mutableStateOf(false) }

    val groupUiState by viewModel.groupUiState.collectAsStateWithLifecycle()
    val scheduleOverviewUiState by viewModel.scheduleOverviewUiState.collectAsStateWithLifecycle()

    if (showDialogNav) {
        NavHost(
            navController = dialogNavController,
            startDestination = GroupSettingDialogRoute.Menu.name
        ) {
            dialog(GroupSettingDialogRoute.Menu.name) {
                SimpleMenuDialog(onDismissRequest = {
                    showDialogNav = false
                }, menus = listOf(
                    SimpleMenu(
                        title = stringResource(id = R.string.analyze_group),
                        onClick = {
                            // TODO 그룹 분석
                        }
                    ),
                    SimpleMenu(
                        title = stringResource(id = R.string.exit_group),
                        onClick = {
                            dialogNavController.navigate(GroupSettingDialogRoute.ExitAlert.name)
                        }
                    )
                ))
            }

            dialog(GroupSettingDialogRoute.ExitAlert.name) {
                DefaultAlertDialog(text = {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.exit_group_message, groupName),
                        style = Subtitle3
                    )
                }, onDismissRequest = {
                    showDialogNav = false
                    dialogNavController.popBackStack(
                        GroupSettingDialogRoute.Menu.name,
                        inclusive = false,
                        saveState = false
                    )
                }, confirmButton = {
                    Text(text = stringResource(id = R.string.exit), style = Subtitle3)
                }, dismissButton = {
                    Text(
                        modifier = Modifier.clickable {
                            showDialogNav = false
                            dialogNavController.popBackStack(
                                GroupSettingDialogRoute.Menu.name,
                                inclusive = false,
                                saveState = false
                            )
                        },
                        text = stringResource(id = R.string.cancel),
                        style = Subtitle3
                    )
                }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            GroupTopAppBar(title = groupName, actions = {
                if (groupUiState is GroupUiState.Success)
                    IconButton(onClick = { showDialogNav = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more),
                            contentDescription = stringResource(
                                id = R.string.group_more_icon_content_description
                            )
                        )
                    }
            })
        },
        floatingActionButton = {
            if (pagerState.currentPage == GroupTabType.SCHEDULE.ordinal) {
                IconFloatingActionButton(
                    onClick = { /*TODO*/ },
                    icon = R.drawable.ic_add,
                    contentDescription = R.string.create_schedule_content_description,
                )
            }
        }
    ) {
        Column(
            modifier = modifier.padding(it),
        ) {
            // TODO 광고 배너
            GroupTabRow(
                modifier = Modifier.fillMaxSize(),
                pagerState = pagerState,
                groupUiState = groupUiState,
                scheduleOverviewUiState = scheduleOverviewUiState,
                onNavigateToWaitingScheduleScreen = onNavigateToWaitingScheduleScreen
            )
        }
    }
}