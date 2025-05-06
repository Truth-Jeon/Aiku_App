package com.aiku.presentation.ui.component.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Subtitle_2G
import com.aiku.presentation.ui.component.chip.OutlinedChip

@Composable
fun EmptyContentView(
    modifier: Modifier = Modifier,
    title: String,
    bottomChipEnabled: Boolean = true,
    onBottomChipClicked: () -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.suggest_create_schedule),
            style = Subtitle_2G
        )
        Image(
            modifier = Modifier
                .size(300.dp)
                .padding(top = 24.dp),
            painter = painterResource(id = R.drawable.char_running_boy),
            contentDescription = stringResource(id = R.string.empty_content_description)
        )
        if (bottomChipEnabled) {
            OutlinedChip(
                modifier = Modifier.padding(top = 24.dp),
                text = title,
                onClick = onBottomChipClicked
            )
        }
    }
}