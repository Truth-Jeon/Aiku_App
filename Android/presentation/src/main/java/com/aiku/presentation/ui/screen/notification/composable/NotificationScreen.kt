package com.aiku.presentation.ui.screen.notification.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle_3G
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray01
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.ui.component.layout.EmptyContentView
import com.aiku.presentation.ui.screen.notification.viewmodel.NotificationUiState
import com.aiku.presentation.ui.screen.notification.viewmodel.NotificationViewModel
import com.aiku.presentation.util.noRippleClickable

enum class NotificationTab(
    @StringRes val tabTitleResId: Int,
    @StringRes val titleResId: Int
) {
    ALL(R.string.notification_all, R.string.notification_all_title),
    SCHEDULE(R.string.schedule, R.string.notification_schedule_title),
    RACING(R.string.notification_racing, R.string.notification_racing),
    AKU(R.string.notification_aku, R.string.notification_aku),
    EVENT(R.string.notification_event, R.string.notification_event),
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {

    var selectedTab by rememberSaveable { mutableStateOf(NotificationTab.ALL) }
    val notificationUiState by notificationViewModel.notificationUiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
    ) {
        item {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth().background(Gray01),
                title = {
                    Text(text = stringResource(R.string.notification), style = Subtitle_3G)
                }
            )
        }

        stickyHeader {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NotificationTab.entries.forEach {
                    TabItemView(
                        title = stringResource(it.tabTitleResId),
                        isSelected = selectedTab == it,
                        onClick = {
                            notificationViewModel.setNotificationCategory(it.name)
                            selectedTab = it
                        },
                    )
                }
            }
        }

        item {
            if (notificationUiState is NotificationUiState.Empty) {
                EmptyContentView(
                    title = stringResource(R.string.no_notification_yet),
                    bottomChipEnabled = false
                )
            }
            else {
                NotificationContentScreen(
                    modifier = Modifier.fillMaxSize().padding(top = 22.dp),
                    notifications = (notificationUiState as? NotificationUiState.Success)?.notifications ?: emptyList(),
                    selectedTab
                )
            }
        }
    }
}

@Composable
private fun TabItemView(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(8.dp)).background(
            if (isSelected) CobaltBlue else Color.White
        ).border(
            width = 1.dp,
            color = if (isSelected) CobaltBlue else Gray02,
            shape = RoundedCornerShape(8.dp)
        ).noRippleClickable { onClick() }.padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = Body2,
            fontWeight = FontWeight.SemiBold,
            color = if(isSelected) Color.White else Gray03
        )
    }
}