package com.aiku.domain.usecase

import com.aiku.domain.repository.TermsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ReadTermsUseCase @Inject constructor(
    private val termsRepository: TermsRepository
) {
    fun execute(resId: Int): Flow<Result<String?>> {
        return termsRepository.getTerms(resId = resId)
    }
}
