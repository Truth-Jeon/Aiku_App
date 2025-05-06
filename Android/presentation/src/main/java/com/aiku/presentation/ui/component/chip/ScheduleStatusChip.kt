package com.aiku.presentation.ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Caption1
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Purple5
import com.aiku.presentation.theme.Yellow5

@Composable
fun ScheduleStatusChip(modifier: Modifier = Modifier, status: ScheduleStatus, isOutlineOnly : Boolean = false) {
    val label = stringResource(
        id = when (status) {
            ScheduleStatus.RUN -> R.string.schedule_running
            ScheduleStatus.WAIT -> R.string.schedule_waiting
            ScheduleStatus.TERM -> R.string.schedule_terminated
            ScheduleStatus.BEFORE_PARTICIPATION -> R.string.schedule_before_participation
        }
    )

    val color = if(isOutlineOnly) Color.Transparent else when (status) {
        ScheduleStatus.RUN -> Green5
        ScheduleStatus.WAIT -> Purple5
        ScheduleStatus.TERM -> Gray03
        ScheduleStatus.BEFORE_PARTICIPATION -> Yellow5
    }

    Box(
        modifier = modifier.background(
            color = color,
            shape = RoundedCornerShape(10.dp)
        )
            .border(width = if(isOutlineOnly) 1.dp else 0.dp, color = Color.White, shape = RoundedCornerShape(999.dp))
            .padding(vertical = 4.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            style = Caption1,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, name = "RUN")
@Composable
private fun ScheduleRunStatusChipPreview(modifier: Modifier = Modifier) {
    ScheduleStatusChip(modifier = modifier, status = ScheduleStatus.RUN)
}

@Preview(showBackground = true, name = "WAIT")
@Composable
private fun ScheduleWaitStatusChipPreview(modifier: Modifier = Modifier) {
    ScheduleStatusChip(modifier = modifier, status = ScheduleStatus.WAIT)
}

@Preview(showBackground = true, name = "TERM")
@Composable
private fun ScheduleTermStatusChipPreview(modifier: Modifier = Modifier) {
    ScheduleStatusChip(modifier = modifier, status = ScheduleStatus.TERM)
}

@Preview(showBackground = true, name = "BEFORE_PARTICIPATION")
@Composable
private fun ScheduleBeforeParticipationStatusChipPreview(modifier: Modifier = Modifier) {
    ScheduleStatusChip(modifier = modifier, status = ScheduleStatus.BEFORE_PARTICIPATION)
}

@Preview(showBackground = false, name = "OUTLINE_ONLY")
@Composable
private fun ScheduleOutlineStatusChipPreview(modifier: Modifier = Modifier) {
    ScheduleStatusChip(modifier = modifier, status = ScheduleStatus.TERM, isOutlineOnly = true)
}