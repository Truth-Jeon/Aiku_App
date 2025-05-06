package com.aiku.presentation.state.user

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.aiku.domain.model.user.Term
import com.aiku.domain.model.user.Terms
import com.aiku.domain.model.user.type.TermAgreeType
import com.aiku.domain.model.user.type.TermType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Immutable
data class TermsViewState(
    val terms: List<TermViewState>
)

@Parcelize
@Serializable
data class TermViewState(
    val title: TermType,
    val content: String,
    val agreedType: TermAgreeType
) : Parcelable

fun Term.toTermViewState() = TermViewState(
    title = title,
    content = content,
    agreedType = agreedType
)

fun Terms.toTermsViewState() = TermsViewState(
    terms = terms.map { it.toTermViewState() }
)