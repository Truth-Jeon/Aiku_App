package com.aiku.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aiku.core.R

enum class DefaultCharacter(
    @DrawableRes val characterRes: Int,
    @StringRes val characterName: Int
) {
    MAN(R.drawable.char_head_boy, R.string.character_man),
    BABY(R.drawable.char_head_baby, R.string.character_baby),
    SCRATCH(R.drawable.char_head_scratch, R.string.character_scratch),
    GIRL(R.drawable.char_head_girl, R.string.character_girl),
}