package com.aiku.presentation.ui.type

import androidx.annotation.StringRes
import com.aiku.core.R

enum class GroupTabType(
    @StringRes val titleResId: Int
) {
    MEMBER(R.string.member), SCHEDULE(R.string.schedule)
}