package com.aiku.data.dto.user

import com.aiku.domain.model.user.Term
import com.aiku.domain.model.user.Terms
import com.aiku.domain.model.user.type.TermAgreeType
import com.aiku.domain.model.user.type.TermType
import com.squareup.moshi.Json

data class TermsDto(
    val terms: List<TermDto>?,
)

data class TermDto(
    @Json(name = "title") val title: String?,
    @Json(name = "content") val content: String?,
    @Json(name = "agreed_type") val agreedType: String?,
)

fun TermsDto.toTerms() = Terms(
    terms = terms?.map { it.toTerm() } ?: emptyList()
)

fun TermDto.toTerm() = Term(
    title = TermType.valueOf(title ?: TermType.SERVICE.name),
    content = content ?: "",
    agreedType = TermAgreeType.valueOf(agreedType ?: TermAgreeType.MANDATORY.name)
)