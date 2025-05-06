package com.aiku.presentation.ui.component.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun DefaultAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable() (() -> Unit)? = null,
    icon: @Composable() (() -> Unit)? = null,
    title: @Composable() (() -> Unit)? = null,
    text: @Composable() (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    containerColor: Color = Color.White,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties
    )
}

@Preview
@Composable
private fun DefaultAlertDialogPreview() {
    DefaultAlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Text("확인")
        },
        dismissButton = {
            Text("취소")
        },
        icon = {},
        title = {
            Text("다이얼로그 제목")
        },
        text = {
            Text("다이얼로그 내용을 적어요 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.")
        }
    )
}