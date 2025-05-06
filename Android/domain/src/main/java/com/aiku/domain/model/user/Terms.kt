package com.aiku.domain.model.user

import com.aiku.domain.model.user.type.TermAgreeType
import com.aiku.domain.model.user.type.TermType

data class Terms(
    val terms: List<Term>
)

data class Term(
    val title: TermType,
    val content: String,
    val agreedType: TermAgreeType
)