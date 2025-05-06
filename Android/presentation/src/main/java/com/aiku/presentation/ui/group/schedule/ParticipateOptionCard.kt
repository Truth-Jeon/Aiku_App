package com.aiku.presentation.ui.group.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Caption1
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.ui.component.image.DefaultBackgroundProfileIcon
import com.aiku.presentation.ui.component.image.DefaultProfileIcon
import com.aiku.presentation.util.noRippleClickable

@Composable
fun ParticipateOptionCard(
    modifier: Modifier = Modifier,
    optionTitle: String,
    participateOption: ParticipateOption,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(12.dp),
    isSelected: Boolean = false
) {

    Column(
        modifier = modifier
            .border(
                shape = shape,
                border = if (isSelected) BorderStroke(3.dp, CobaltBlue) else BorderStroke(
                    1.dp,
                    Gray02
                )
            )
            .fillMaxSize()
            .noRippleClickable {
                onClick()
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = optionTitle, style = Body2, fontWeight = FontWeight.Bold)
        DefaultBackgroundProfileIcon(
            modifier = Modifier
                .padding(top = 12.dp)
                .size(64.dp),
            character = ProfileCharacter.C03,
            enabled = false,
            background = if (participateOption == ParticipateOption.NORMAL) ProfileBackground.GREEN else ProfileBackground.RED
        )
        Spacer(modifier = Modifier.weight(1f))
        when (participateOption) {
            ParticipateOption.NORMAL -> {
                Text(
                    text = stringResource(id = R.string.participation_fee),
                    modifier = Modifier.padding(top = 12.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_aku),
                        contentDescription = stringResource(id = R.string.participation_fee),
                    )
                    Text(text = "100", style = Body2, modifier = Modifier.padding(start = 4.dp))
                }
            }

            ParticipateOption.HANDICAP -> {
                Column {
                    Text(
                        text = stringResource(id = R.string.require_advertise_for_handicap),
                        modifier = Modifier.padding(top = 12.dp),
                        style = Caption1,
                        maxLines = 1
                    )
                    Text(
                        text = stringResource(id = R.string.alert_1vs1_racing_unavailable),
                        modifier = Modifier.padding(top = 4.dp),
                        style = Caption1,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ParticipateOptionCardPreview() {
    ParticipateOptionCard(
        optionTitle = "일반 참가",
        participateOption = ParticipateOption.NORMAL,
        onClick = {}
    )
}