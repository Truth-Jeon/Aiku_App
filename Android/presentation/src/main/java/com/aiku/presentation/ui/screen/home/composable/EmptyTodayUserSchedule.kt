package com.aiku.presentation.ui.screen.home.composable

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Caption1_Medium
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.ui.component.shape.DashBorderBox

private val CharHeadUnknownSize = 46.dp
private val DashedBorderWidth = 1.dp
private val RoundedDottedBorderBoxRadius = 10.dp

@Composable
fun EmptyTodayUserSchedule(
    modifier: Modifier = Modifier
){
    DashBorderBox(
        modifier = Modifier.size(140.dp, 125.dp),
        cornerRadius = RoundedDottedBorderBoxRadius,
        borderColor = Gray03,
        borderWidth = DashedBorderWidth
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(CharHeadUnknownSize),
                painter = painterResource(id = R.drawable.char_head_unknown_home),
                contentDescription = "char head unknown"
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(id = R.string.home_no_schedule),
                style = Caption1_Medium
            )
        }
    }
}

@Preview(showBackground = true, name = "Empty Today Schedule")
@Composable
private fun EmptyTodaySchedulePreview() {
    EmptyTodayUserSchedule()
}