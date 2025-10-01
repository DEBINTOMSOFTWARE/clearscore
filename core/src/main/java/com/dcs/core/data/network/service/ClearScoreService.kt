package com.dcs.core.data.network.service

import com.dcs.core.data.model.ClearScoreApiResponse
import com.dcs.core.data.network.NetworkResult

interface ClearScoreService {
    suspend fun getCreditScore(): NetworkResult<ClearScoreApiResponse>
}