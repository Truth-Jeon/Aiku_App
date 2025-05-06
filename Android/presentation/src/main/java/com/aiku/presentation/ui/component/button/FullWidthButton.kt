package com.aiku.presentation.ui.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    background: ButtonColors,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        colors = background,
        shape = RoundedCornerShape(FullWidthButtonCornerRadius),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues( // 미설정 시, material3 Button 기본 패딩 있음
            start = 0.dp,
            end = 0.dp,
            top = FullWidthButtonVerticalPadding,
            bottom = FullWidthButtonVerticalPadding
        ),
        content = { content() }
    )
}

val FullWidthButtonVerticalPadding = 15.dp //FullWidthBtn 내부 수직 방향 패딩
val FullWidthButtonCornerRadius = 10.dp //FullWidthBtn 모서리 둥글기