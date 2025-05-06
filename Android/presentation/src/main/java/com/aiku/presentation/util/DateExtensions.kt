package com.aiku.presentation.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.abs

/**
 * ex) 2024.09.26 목 or 2024.09.26
 */
fun LocalDateTime.toDefaultDateFormat(withDayOfWeek: Boolean): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd", Locale.KOREA)
    return if (withDayOfWeek) {
        "${this.format(formatter)} ${this.getDayOfWeekKoreanShort()}"
    } else {
        this.format(formatter)
    }
}

/**
 * ex) 화
 */
fun LocalDateTime.getDayOfWeekKoreanShort(): String {
    return this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN).substring(0, 1)
}

/**
 * ex) 오전 09:00
 */
fun LocalDateTime.to12TimeFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA)
    return this.format(formatter)
}

/**
 * ex) 13:00
 */
fun LocalDateTime.to24TimeFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

/**
 * 현재 시간과의 차이를 초 단위로 반환
 */
fun LocalDateTime.getSecondsDifferenceFromNow(): Long {
    return Duration.between(LocalDateTime.now(ZoneId.of("Asia/Seoul")), this).seconds.coerceAtLeast(0)
}

/**
 * 초를 HH : MM : SS 형태로 변환
 */
fun Long.formatSecondsToHHMMSS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format(Locale.KOREA, "%02d : %02d : %02d", hours, minutes, remainingSeconds)
}

