package com.dcs.core.data.repository

import com.dcs.core.data.mapper.toDomainModel
import com.dcs.core.data.network.NetworkResult
import com.dcs.core.data.network.service.ClearScoreService
import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.repository.ClearScoreRepository
import javax.inject.Inject

class ClearScoreRepositoryImpl @Inject constructor(
    private val clearScoreService: ClearScoreService,
): ClearScoreRepository {
    override suspend fun getCreditScore(): DomainResult<ClearScoreDomainModel> {
       return when (val result = clearScoreService.getCreditScore()) {
           is NetworkResult.Success -> DomainResult.Success(
               result.data.toDomainModel()
           )
           is NetworkResult.Error -> DomainResult.Error(result.exception)
       }

    }
}