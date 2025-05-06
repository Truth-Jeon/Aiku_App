package com.aiku.presentation.ui.component.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green5
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularTimeGraph(
    modifier: Modifier = Modifier,
    contentColor: Color = Green5,
    extraColor: Color = Gray03,
    width: Float = 20f,
    totalTime: Long,
    elapsedTime: Long
) {
    Canvas(modifier = modifier) {
        val ratio = (elapsedTime.toDouble() / totalTime)
        val radius = size.width / 2
        val sweepAngle = (ratio * 360)
        val capAngle = (sweepAngle - 90) - 360
        val capOffsetX = -cos(Math.toRadians(capAngle)).toFloat() * radius + size.width / 2
        val capOffsetY = sin(Math.toRadians(capAngle)).toFloat() * radius + size.height / 2
        val capOffset = Offset(capOffsetX, capOffsetY)

        drawArc(
            size = size,
            color = extraColor,
            startAngle = 270f - sweepAngle.toFloat(),
            useCenter = false,
            sweepAngle = sweepAngle.toFloat(),
            style = Stroke(width, cap = StrokeCap.Round)
        )
        drawArc(
            size = size,
            color = contentColor,
            startAngle = 270f,
            useCenter = false,
            sweepAngle = (360 - sweepAngle).toFloat(),
            style = Stroke(width, cap = StrokeCap.Round)
        )
        drawCircle(
            color = contentColor,
            center = capOffset,
            radius = 30f
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CircularTimeGraphPreview() {
    CircularTimeGraph(
        modifier = Modifier
            .padding(10.dp)
            .size(200.dp),
        totalTime = 1800,
        elapsedTime = 1200
    )
}