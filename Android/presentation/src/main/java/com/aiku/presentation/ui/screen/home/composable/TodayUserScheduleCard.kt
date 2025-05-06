package com.aiku.presentation.ui.screen.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Caption1
import com.aiku.core.theme.Caption1_Medium
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.domain.model.schedule.type.ScheduleStatus
import com.aiku.presentation.state.schedule.LocationState
import com.aiku.presentation.state.schedule.UserScheduleOverviewState
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Purple5
import com.aiku.presentation.ui.component.chip.ScheduleStatusChip
import com.aiku.presentation.ui.component.shadow.defaultShadow
import com.aiku.presentation.util.to24TimeFormat
import java.time.LocalDateTime

@Composable
fun TodayUserScheduleCard(
    modifier: Modifier = Modifier,
    schedule: UserScheduleOverviewState,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .size(140.dp, 125.dp)
            .defaultShadow(),
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick() },
        colors = CardDefaults.cardColors().copy(
            containerColor = when (schedule.status) {
                ScheduleStatus.RUN -> Green5
                ScheduleStatus.WAIT -> Purple5
                else -> Gray03
            }
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = schedule.groupName,
                style = Caption1
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = schedule.time.to24TimeFormat(),
                style = Subtitle3_SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = schedule.location.name,
                style = Caption1_Medium
            )
            ScheduleStatusChip(modifier = Modifier.padding(top = 22.dp), status = schedule.status, isOutlineOnly = true)
        }
    }

}

@Preview(showBackground = true, name = "Today Schedule Card")
@Composable
private fun TodayScheduleCardPreview() {
    TodayUserScheduleCard(
        onClick = { /*TODO : navigate to groupdetail*/ },
        schedule = UserScheduleOverviewState(
            1,
            "그룹이름",
            10,
            "스케쥴이름",
            LocationState(138.2, 142.7, "장소이름"),
            LocalDateTime.now(),
            ScheduleStatus.RUN,
            10
        ),
        modifier = Modifier.fillMaxWidth()
    )
}