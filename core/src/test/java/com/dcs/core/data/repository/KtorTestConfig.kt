package com.dcs.core.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorTestConfig {
    companion object {
        fun createMockEngine(
            statusCode: HttpStatusCode = HttpStatusCode.OK,
            content: String = "",
            contentType: ContentType = ContentType.Application.Json,
            headers: Map<String, List<String>> = emptyMap(),
            throwException: Exception? = null
        ): MockEngine {
            // Fix: corrected parameter name from "hesders" to "headers"
            // Fix: proper headers merging
            val headersList = buildList {
                add(HttpHeaders.ContentType to listOf(contentType.toString()))
                addAll(headers.entries.map { it.key to it.value })
            }
            val allHeaders = headersOf(*headersList.toTypedArray())

            return MockEngine { _ ->
                throwException?.let { throw it }

                when {
                    statusCode.value in 200..299 -> respond(content, statusCode, allHeaders)
                    else -> respondError(status = statusCode, headers = allHeaders, content = content)
                }
            }
        }

        fun createMockClient(
            mockEngine: MockEngine,
            enableJson: Boolean = true
        ): HttpClient {
            return HttpClient(mockEngine) {
                followRedirects = false
                expectSuccess = true
                if (enableJson) {
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            }
        }
    }
}