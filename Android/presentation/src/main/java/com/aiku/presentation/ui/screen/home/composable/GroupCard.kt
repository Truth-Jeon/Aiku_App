package com.aiku.presentation.ui.screen.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle3_Bold
import com.aiku.presentation.state.group.GroupOverviewState
import com.aiku.presentation.theme.ColorStripCardStartPadding
import com.aiku.presentation.theme.Purple80
import com.aiku.presentation.ui.component.shadow.defaultShadow
import java.time.LocalDateTime

@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    color: Color,
    group: GroupOverviewState,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.defaultShadow(),
        shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp),
        onClick = { onClick() },
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = color,
                        topLeft = Offset(0f, 0f),
                        size = Size(24f, size.height)
                    )
                }
                .padding(vertical = 16.dp, horizontal = ColorStripCardStartPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = group.name,
                    style = Subtitle3_Bold
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = group.lastScheduleTime.toString(),
                    style = Body2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Image(
                    modifier = Modifier.size(58.dp),
                    painter = painterResource(id = R.drawable.char_head_nohair),
                    contentDescription = stringResource(id = R.string.character_nohair)
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 4.dp),
                    text = group.memberSize.toString(),
                    style = Subtitle3_Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Group Card")
@Composable
private fun GroupCardPreview() {
    GroupCard(
        color = Purple80,
        onClick = { /*TODO : navigate to groupdetail*/ },
        group = GroupOverviewState(
            1,
            "그룹이름",
            10,
            LocalDateTime.now()
        ),
        modifier = Modifier.fillMaxWidth()
    )
}