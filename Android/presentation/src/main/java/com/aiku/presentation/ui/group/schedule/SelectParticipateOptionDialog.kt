package com.aiku.presentation.ui.group.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body1
import com.aiku.presentation.ui.component.dialog.SingleButtonDialog

enum class ParticipateOption {
    NORMAL, HANDICAP
}

@Composable
fun SelectParticipateOptionDialog(
    onEnterClicked: (ParticipateOption) -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    var selectedOption by remember {
        mutableStateOf(ParticipateOption.NORMAL)
    }

    SingleButtonDialog(
        onButtonClicked = { onEnterClicked(selectedOption) },
        onDismissRequest = onDismissRequest,
        buttonText = stringResource(id = R.string.enter_schedule)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.select_participate_option), style = Body1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ParticipateOptionCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    optionTitle = stringResource(id = R.string.participate_option_normal),
                    participateOption = ParticipateOption.NORMAL,
                    onClick = { selectedOption = ParticipateOption.NORMAL },
                    isSelected = selectedOption == ParticipateOption.NORMAL
                )
                Spacer(modifier = Modifier.width(16.dp))
                ParticipateOptionCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    optionTitle = stringResource(id = R.string.participate_option_handicap),
                    participateOption = ParticipateOption.HANDICAP,
                    onClick = { selectedOption = ParticipateOption.HANDICAP },
                    isSelected = selectedOption == ParticipateOption.HANDICAP
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectParticipateOptionDialogPreview() {
    SelectParticipateOptionDialog(onEnterClicked = {})
}