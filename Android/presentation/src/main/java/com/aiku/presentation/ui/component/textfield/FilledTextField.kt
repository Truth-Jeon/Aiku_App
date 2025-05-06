package com.aiku.presentation.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Caption1
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.util.getMeasuredKoreanHeight

@Composable
fun FilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    showTextCounter: Boolean = false,
    maxLength: Int = 0,
    innerPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    color: Color = Gray02,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = Caption1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val newHeight = textStyle.getMeasuredKoreanHeight()

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
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(5.dp)
                ).padding(innerPadding),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier,
            ) {
                Text(
                    modifier = Modifier.height(newHeight),
                    text = if (value.isEmpty()) placeholder else "",
                    style = textStyle,
                    color = Gray03,
                    maxLines = 1
                )
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        if (showTextCounter)
                            Text(
                                modifier = Modifier.align(Alignment.Bottom),
                                text = "$maxLength / ${value.length}",
                                style = textStyle,
                                color = Gray03,
                            )
                        Spacer(modifier = Modifier.weight(1f))
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            innerTextField()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledTextFieldPreview() {
    FilledTextField(
        value = "Hello, World!",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun FilledTextFieldNoTextPreview() {
    FilledTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        placeholder = "Placeholder"
    )
}


@Preview(showBackground = true)
@Composable
private fun FilledTextFieldInputPreview() {
    FilledTextField(
        value = "텍스트 입력함",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
    )
}


@Preview(showBackground = true)
@Composable
private fun FilledTextFieldLongInputPreview() {
    FilledTextField(
        value = "텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함텍스트 입력함",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        showTextCounter = true,
        maxLength = 200
    )
}

