package com.aiku.domain.repository

import kotlinx.coroutines.flow.Flow

interface TermsRepository {
    fun getTerms(resId: Int): Flow<Result<String?>>
}
