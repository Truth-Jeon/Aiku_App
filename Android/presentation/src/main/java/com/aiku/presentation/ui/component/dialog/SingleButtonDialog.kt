package com.aiku.presentation.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Body1
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.button.FullWidthButton

@Composable
fun SingleButtonDialog(
    onButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    buttonText: String,
    content: @Composable () -> Unit
) {
    MinimalDialog(onDismissRequest = onDismissRequest) {
        Column {
            content()
            FullWidthButton(
                onClick = {
                    onButtonClicked()
                },
                modifier = Modifier.padding(16.dp),
                background = ButtonDefaults.buttonColors().copy(
                    containerColor = Green5,
                    contentColor = Color.White
                )
            ) {
                Text(text = buttonText, style = Body1, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview
@Composable
private fun SingleButtonDialogPreview() {
    SingleButtonDialog(
        onButtonClicked = {},
        onDismissRequest = {},
        buttonText = "확인",
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "여기에 버튼 위의 컨텐트 컴포저블 작성")
            }
        }
    )
}