package com.aiku.presentation.ui.component.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Typo

@Composable
fun CheckBoxWithText(
    modifier: Modifier,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: String
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!checkedState) }
    ) {
        Box(
            modifier = Modifier
                .size(CheckBoxSize)
                .background(
                    color = if (checkedState) Green5 else Gray02,
                    shape = RoundedCornerShape(CheckBoxCornerRadius)
                )
        ) {
            Image(
                modifier = Modifier.size(CheckMarkSize),
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "check mark"
            )
        }

        Text(
            modifier = Modifier.padding(start = CheckBoxContentPadding),
            text = content,
            style = Subtitle3_SemiBold,
            color = Typo
        )
    }
}

private val CheckBoxCornerRadius = 5.dp
private val CheckBoxSize = 22.dp
private val CheckMarkSize = 22.dp

private val CheckBoxContentPadding = 10.dp