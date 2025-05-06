package com.aiku.presentation.ui.component.background

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aiku.presentation.theme.Green

@Composable
fun CircularBorderBackground(
    modifier: Modifier = Modifier,
    color: Color = Green,
    borderWidth: Dp = 1.dp,
    borderColor: Color = color,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    CircularBackground(
        modifier = modifier.border(
            width = borderWidth,
            color = borderColor,
            shape = CircleShape
        ),
        color = color,
        onClick = onClick,
        content = content
    )
}

@Preview(showBackground = true, name = "Circular Border Background")
@Composable
private fun CircularBorderBackgroundPreview() {
    CircularBorderBackground(
        modifier = Modifier.size(100.dp),
        color = Green,
        borderWidth = 1.dp,
        borderColor = Color.Black
    ) {
        // content
    }
}
