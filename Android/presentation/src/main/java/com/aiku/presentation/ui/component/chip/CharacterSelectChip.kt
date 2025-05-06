package com.aiku.presentation.ui.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray02

@Composable
fun CharacterSelectChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    @DrawableRes characterRes: Int,
    contentDescription: String
) {

    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .size(49.dp)
            .background(
                shape = shape,
                color = Gray02
            )
            .border(
                color = if (isSelected) CobaltBlue else Gray02,
                width = 2.dp,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = characterRes), contentDescription = contentDescription)
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterSelectChipPreview() {
    CharacterSelectChip(
        isSelected = true,
        characterRes = R.drawable.char_head_baby,
        contentDescription = "Character Select Chip"
    )
}