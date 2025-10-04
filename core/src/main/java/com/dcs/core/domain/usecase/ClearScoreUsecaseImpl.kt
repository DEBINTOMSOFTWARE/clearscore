package com.dcs.core.domain.usecase

import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.repository.ClearScoreRepository
import javax.inject.Inject


class ClearScoreUsecaseImpl @Inject constructor(
    private val clearScoreRepository: ClearScoreRepository
) : ClearScoreUsecase {
    override suspend fun getCreditScore(): DomainResult<ClearScoreDomainModel> {
        return clearScoreRepository.getCreditScore()
    }
}