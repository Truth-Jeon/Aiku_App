package com.aiku.presentation.ui.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Subtitle3

@Composable
fun SimpleMenuDialog(
    onDismissRequest: () -> Unit,
    menus: List<SimpleMenu>
) {
    MinimalDialog(onDismissRequest = onDismissRequest) {
        repeat(menus.size) { index ->
            val menu = menus[index]
            Text(
                text = menu.title,
                textAlign = TextAlign.Center,
                style = Subtitle3,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        menu.onClick()
                    }
                    .padding(vertical = 16.dp)
            )
            if (index != menus.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

data class SimpleMenu(
    val title: String,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun SimpleMenuDialogPreview() {
    SimpleMenuDialog(
        onDismissRequest = {},
        menus = listOf(
            SimpleMenu("안녕하세요") {},
            SimpleMenu("반가워요") {},
            SimpleMenu("잘있어요") {}
        )
    )
}