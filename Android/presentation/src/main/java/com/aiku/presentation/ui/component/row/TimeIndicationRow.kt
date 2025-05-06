package com.aiku.presentation.ui.component.row

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Caption1
import com.aiku.presentation.util.to12TimeFormat
import com.aiku.presentation.util.toDefaultDateFormat
import java.time.LocalDateTime

@Composable
fun TimeIndicationRow(
    modifier: Modifier = Modifier,
    time: LocalDateTime,
    textStyle: TextStyle = Body2,
    color: Color = Color.Black
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Text(
            text = time.toDefaultDateFormat(true),
            style = textStyle,
            color = color,
        )
        VerticalDivider(thickness = 1.dp, color = color, modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            text = time.to12TimeFormat(),
            style = textStyle,
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeIndicationRowPreview() {
    TimeIndicationRow(
        time = LocalDateTime.now()
    )
}