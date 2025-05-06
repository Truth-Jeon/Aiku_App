package com.aiku.presentation.ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aiku.presentation.theme.CobaltBlue

@Composable
fun FilledChip(
    modifier: Modifier = Modifier,
    containerColor: Color = CobaltBlue,
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.clip(
            RoundedCornerShape(50f)
        ).background(containerColor)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}