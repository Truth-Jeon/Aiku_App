package com.aiku.presentation.navigation.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aiku.core.R


enum class BtmNav(@StringRes val labelId: Int, @DrawableRes val icon: Int) {
    Home(R.string.nav_home, R.drawable.btm_nav_home),
    MySchedule(R.string.nav_mySchedule, R.drawable.btm_nav_schedule),
    My(R.string.nav_mypage, R.drawable.btm_nav_my)
}