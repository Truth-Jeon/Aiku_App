package com.aiku.presentation.ui.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Caption1
import com.aiku.presentation.theme.ColorError
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Gray03

/**
 * @param label 하단 라벨
 * @param showLabel 라벨 표시 여부. true일 경우 label이 비어있더라도 공간을 차지함.
 * @param placeholder placeholder
 * @param lineColor 하단 라인 색상
 * @param showLengthIndicator 글자 수 표시 여부
 * @param maxLength 최대 글자 수
 */
@Composable
fun BottomLinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String = "",
    showLabel: Boolean = false,
    placeholder: String = "",
    lineColor: Color = Gray02,
    showLengthIndicator: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
) {

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = @Composable { innerTextField ->
            Column(
                modifier = Modifier,
            ) {
                Row {
                    Box() {
                        if (value.isEmpty())
                            Text(text = placeholder, color = Gray03, style = textStyle)
                        innerTextField()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (showLengthIndicator) {
                        Text(
                            color = if (value.length > maxLength) ColorError else Gray03,
                            text = "${value.length}/${maxLength}",
                            style = Caption1
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp),
                    color = lineColor,
                    thickness = 1.dp
                )
                if (showLabel)
                    Text(text = label.ifEmpty { " " }, style = Caption1, modifier = Modifier.padding(top = 4.dp))
            }
        }
    )
}


@Preview(showBackground = true, name = "Bottom Lined TextField")
@Composable
fun BottomLinedTextFieldPreview() {
    BottomLinedTextField(
        value = "닉네임",
        onValueChange = {},
        label = "최대 6글자 입력 가능합니다.",
        showLabel = true,
        placeholder = "닉네임을 입력하세요",
        lineColor = Gray02,
        showLengthIndicator = true,
        maxLength = 6,
    )
}

@Preview(showBackground = true, name = "Bottom Lined TextField - No Text")
@Composable
fun BottomLinedTextFieldNoTextPreview() {
    BottomLinedTextField(
        value = "",
        onValueChange = {},
        label = "최대 6글자 입력 가능합니다.",
        showLabel = true,
        placeholder = "닉네임을 입력하세요",
        lineColor = Gray02,
        showLengthIndicator = true,
        maxLength = 6,
    )
}
