package com.aiku.presentation.util

import androidx.compose.ui.graphics.Color
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.core.R
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.theme.Purple5
import com.aiku.presentation.theme.Yellow5

fun ProfileCharacter.getDrawableResId(): Int {
    return when (this) {
        ProfileCharacter.C01 -> R.drawable.char_head_boy
        ProfileCharacter.C02 -> R.drawable.char_head_baby
        ProfileCharacter.C03 -> R.drawable.char_head_scratch
        ProfileCharacter.C04 -> R.drawable.char_head_girl
        ProfileCharacter.NONE -> R.drawable.char_head_boy
    }
}

fun ProfileBackground.getColor(): Color {
    return when (this) {
        ProfileBackground.YELLOW -> Yellow5
        ProfileBackground.GREEN -> Green5
        ProfileBackground.PURPLE -> Purple5
        ProfileBackground.GRAY -> Gray03
        ProfileBackground.RED -> Color.Red
    }
}