package com.aiku.presentation.ui.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.presentation.theme.CobaltBlue

@Composable
fun OutlinedChip(
    modifier: Modifier = Modifier,
    @DrawableRes navigationIcon: Int = -1,
    contentColor: Color = CobaltBlue,
    borderColor: Color = CobaltBlue,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(999.dp))
            .clip(RoundedCornerShape(16.dp))
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(999.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        if (navigationIcon != -1)
            Icon(
                painter = painterResource(id = navigationIcon),
                contentDescription = stringResource(id = R.string.chip_navigation_icon_content_description),
                tint = contentColor,
            )
        Text(text = text, modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp), color = contentColor)
    }
}

@Preview
@Composable
private fun OutlinedChipPreview() {
    OutlinedChip(text = "OutlinedChip")
}