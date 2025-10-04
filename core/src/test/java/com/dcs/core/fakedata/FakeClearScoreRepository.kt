package com.dcs.core.fakedata

import com.dcs.core.data.network.NetworkResult
import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.repository.ClearScoreRepository

class FakeClearScoreRepository : ClearScoreRepository {

    private var response: DomainResult<ClearScoreDomainModel> =
        DomainResult.Error(Exception("No response set"))

    fun setResponse(result: DomainResult<ClearScoreDomainModel>) {
        response = result
    }

    override suspend fun getCreditScore(): DomainResult<ClearScoreDomainModel> {
      return response
    }
}