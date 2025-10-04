package com.dcs.core.data.repository

import com.dcs.core.data.network.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CancellationException

inline fun <T> HttpClient.safeHttpRequest(
    block: () -> T
): NetworkResult<T> = try {
    NetworkResult.Success(block())
} catch (e: ClientRequestException) { // 4xx errors
    NetworkResult.Error(Exception("Client error (${e.response.status.value}): ${e.message}"))
} catch (e: ServerResponseException) { // 5xx errors
    NetworkResult.Error(Exception("Server error (${e.response.status.value}): ${e.message}"))
} catch (e: RedirectResponseException) { // 3xx errors
    NetworkResult.Error(Exception("Redirect error (${e.response.status.value}): ${e.message}"))
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    NetworkResult.Error(e)
}