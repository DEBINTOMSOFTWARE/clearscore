package com.dcs.core.data.network.service

import com.dcs.core.data.model.ClearScoreApiResponse
import com.dcs.core.data.network.NetworkResult
import com.dcs.core.data.repository.safeHttpRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ClearScoreServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ClearScoreService {
    override suspend fun getCreditScore(): NetworkResult<ClearScoreApiResponse> {
        return httpClient.safeHttpRequest {
            httpClient.get("endpoint.json").body()
        }
    }
}