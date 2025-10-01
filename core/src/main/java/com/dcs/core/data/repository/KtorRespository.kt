package com.dcs.core.data.repository

import com.dcs.core.data.network.NetworkResult
import io.ktor.client.HttpClient

inline fun <T> HttpClient.safeHttpRequest(
    block: () -> T
): NetworkResult<T> = try {
    NetworkResult.Success(block())
} catch (e: Exception) {
    NetworkResult.Error(e)
}