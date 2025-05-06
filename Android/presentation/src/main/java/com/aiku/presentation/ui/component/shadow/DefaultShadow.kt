package com.aiku.presentation.ui.component.shadow

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.defaultShadow(
    cornerShape: RoundedCornerShape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp)
): Modifier {
    return this.shadow(
        elevation = 6.dp,
        shape = cornerShape,
        ambientColor = Color.Gray.copy(alpha = 0.8f),
        spotColor = Color.Black.copy(alpha = 0.4f)
    )
}