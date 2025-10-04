package com.dcs.core.fakedata

import com.dcs.core.DataFactory
import com.dcs.core.data.model.ClearScoreApiResponse
import com.dcs.core.data.network.NetworkResult
import com.dcs.core.data.network.service.ClearScoreService
import com.dcs.core.domain.model.ClearScoreDomainModel

class FakeClearScoreService: ClearScoreService {
    var shouldThrowException: Boolean = false
    var successResponse: ClearScoreApiResponse? = null
    var errorException : Exception = Exception("Default error")

    override suspend fun getCreditScore(): NetworkResult<ClearScoreApiResponse> {
        if (shouldThrowException) throw errorException
        return if (successResponse != null) {
            NetworkResult.Success(successResponse!!)
        } else {
            NetworkResult.Error(errorException)
        }
    }
}