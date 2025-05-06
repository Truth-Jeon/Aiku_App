package com.aiku.presentation.ui.screen.my.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.topbar.DefaultTopAppBar
import com.aiku.presentation.ui.screen.my.viewmodel.NotificationSettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationSettingViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        DefaultTopAppBar(
            title = stringResource(R.string.item_notification_setting)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NotificationSettingItem(
            modifier = Modifier.padding(20.dp),
            title = stringResource(R.string.notification_item_push),
            isChecked = true,
            onSwitchChanged = {}
        )
        HorizontalDivider(
            color = Gray02
        )
        NotificationSettingItem(
            modifier = Modifier.padding(20.dp),
            title = stringResource(R.string.notification_item_advertisement),
            isChecked = true,
            onSwitchChanged = {}
        )
    }
}

@Composable
private fun NotificationSettingItem(
    title: String,
    isChecked: Boolean,
    onSwitchChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Body2
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onSwitchChanged,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Green5,
                checkedThumbColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationSettingItemPreview() {
    NotificationSettingItem(
        title = "알림 설정",
        isChecked = true,
        onSwitchChanged = {}
    )
}