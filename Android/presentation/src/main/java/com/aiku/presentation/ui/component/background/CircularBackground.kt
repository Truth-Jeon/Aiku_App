package com.aiku.presentation.ui.component.background

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aiku.presentation.theme.Green
import com.aiku.presentation.util.noRippleClickable

/**
 * 클릭 범위를 원으로만 한정하려면 onClick을 사용해야 함
 */
@Composable
fun CircularBackground(
    modifier: Modifier = Modifier,
    color: Color = Green,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    showClickRipple: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .aspectRatio(1f)
            .clip(CircleShape)
            .then(
                if (enabled) {
                    if (showClickRipple) Modifier.clickable(onClick = onClick)
                    else Modifier.noRippleClickable(onClick = onClick)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true, name = "Circular Background")
@Composable
private fun CircularBackgroundPreview() {
    CircularBackground(
        modifier = Modifier.size(100.dp),
        color = Green
    ) {
        // content
    }
}
