package com.dcs.core.domain.usecase

import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel

interface ClearScoreUsecase {
    suspend fun getCreditScore(): DomainResult<ClearScoreDomainModel>
}