package com.aiku.presentation.util

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

/**
 * Ripple 효과 없는 clickable
 */
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}

@Composable
fun TextStyle.getMeasuredKoreanHeight(): Dp {
    val textMeasurer = rememberTextMeasurer()
    val koreanTextHeight = remember(this, textMeasurer) {
        textMeasurer.measure(
            text = "가",
            style = copy(textAlign = TextAlign.Center)
        ).size.height
    }
    return with(LocalDensity.current) { koreanTextHeight.toDp() }
}