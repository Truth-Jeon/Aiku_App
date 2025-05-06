package com.aiku.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aiku.core.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val gmarketFontFamily = FontFamily(
    Font(R.font.gmarket_sans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.gmarket_sans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.gmarket_sans_light, FontWeight.Light, FontStyle.Normal),
)

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_variable, FontWeight.Normal, FontStyle.Normal),
)

val Headline_1G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 60.sp,
    lineHeight = 80.sp,
    letterSpacing = 0.sp
)

val Headline_2G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 34.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp
)

val Headline_3G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp
)

val Subtitle_1G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 34.sp,
    letterSpacing = 0.sp
)

val Subtitle_2G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 30.sp,
    letterSpacing = 0.sp
)

val Subtitle_3G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp
)

val Subtitle_4G = TextStyle(
    fontFamily = gmarketFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val Headline1 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 30.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp
)

val Headline2 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
)

val Subtitle1 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 30.sp,
    letterSpacing = 0.sp
)

val Subtitle2 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.sp
)

val Subtitle3 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val Subtitle3_Medium = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val Subtitle3_SemiBold = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val Subtitle3_Bold = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val Body1 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.sp
)

val Body1_SemiBold = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.sp
)

val Body2 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val Body2_Medium = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val Body2_SemiBold = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp
)

val Caption1 = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
)

val Caption1_Medium = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
)