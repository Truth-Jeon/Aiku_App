package com.aiku.presentation.ui.screen.notification.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Body1_SemiBold
import com.aiku.core.theme.Caption1
import com.aiku.presentation.state.notification.NotificationViewState
import com.aiku.presentation.theme.Gray03

@Composable
fun NotificationContentScreen(
    modifier: Modifier = Modifier,
    notifications: List<NotificationViewState>,
    category: NotificationTab
) {

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = stringResource(category.titleResId),
                style = Body1_SemiBold
            )
        }

        items(
            items = notifications,
            key = { notification -> notification.id }
        ) {
            NotificationContentItem(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                notification = it
            )
        }
    }
}

@Composable
private fun NotificationContentItem(
    modifier: Modifier = Modifier,
    notification: NotificationViewState
) {
    Column(
        modifier = modifier.clip(
            RoundedCornerShape(10.dp)
        ).background(Color.White).padding(horizontal = 14.dp, vertical = 16.dp)
    ) {
        Text(
            text = notification.title,
            style = Body1_SemiBold
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = notification.content,
            style = Caption1,
            color = Gray03
        )
    }
}