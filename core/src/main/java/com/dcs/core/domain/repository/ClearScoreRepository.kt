package com.dcs.core.domain.repository

import com.dcs.core.data.network.NetworkResult
import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel

interface ClearScoreRepository {
    suspend fun getCreditScore() : DomainResult<ClearScoreDomainModel>
}