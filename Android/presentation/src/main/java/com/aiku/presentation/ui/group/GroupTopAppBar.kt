package com.aiku.presentation.ui.group

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.aiku.core.R
import com.aiku.core.theme.Subtitle_3G

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, style = Subtitle_3G)
        },
        actions = actions
    )
}