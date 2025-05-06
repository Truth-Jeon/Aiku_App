package com.aiku.data.repository

import com.aiku.data.source.local.TermsLocalDataSource
import com.aiku.domain.repository.TermsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TermsRepositoryImpl @Inject constructor(
    private val termsLocalDataSource: TermsLocalDataSource
) : TermsRepository {

    override fun getTerms(resId: Int): Flow<Result<String?>> = flow {
        val result = termsLocalDataSource.readTermsFromRaw(resId)
        emit(result)
    }
}
