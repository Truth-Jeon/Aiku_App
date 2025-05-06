package com.aiku.data.dto.schedule.request

import com.aiku.domain.model.schedule.request.BetAkuReq
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BetAkuReqDto(
    @Json(name = "beteeMemberId") val beteeMemberId: Long,
    @Json(name = "pointAmount") val pointAmount: Int
)

fun BetAkuReq.toBetAkuReqDto() = BetAkuReqDto(
    beteeMemberId = beteeMemberId,
    pointAmount = pointAmount
)