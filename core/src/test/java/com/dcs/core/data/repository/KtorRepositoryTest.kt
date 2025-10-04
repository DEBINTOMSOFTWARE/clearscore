package com.dcs.core.data.repository

import com.dcs.core.data.network.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertFailsWith

class KtorRepositoryTest {

    @Test
    fun `safeHttpRequest returns Success on successful response`() = runTest {
        val jsonResponse = """{"key": "value"}"""
        val mockEngine = KtorTestConfig.createMockEngine(
            content = jsonResponse
        )
        val client = KtorTestConfig.createMockClient(mockEngine)
        val result = client.safeHttpRequest {
            client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
        }
        assertTrue(result is NetworkResult.Success)
        assertEquals(jsonResponse, (result as NetworkResult.Success).data)
    }

    @Test
    fun `safeHttpRequest returns Error on client error response`() = runTest {
        val mockEngine = KtorTestConfig.createMockEngine(
            statusCode = HttpStatusCode.BadRequest,
            content = "Bad Request",
            contentType = ContentType.Text.Plain
        )
        val client = KtorTestConfig.createMockClient(mockEngine)
        val result = client.safeHttpRequest {
            client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
        }
        assertTrue(result is NetworkResult.Error)
        assertEquals(
            "Client error (400): Client request(GET https://android-interview.s3.eu-west-2.amazonaws.com) invalid: 400 Bad Request. Text: \"Bad Request\"",
            (result as NetworkResult.Error).exception.message
        )
    }

    @Test
    fun `safeHttpRequest returns Error on server error response`() = runTest {
        val mockEngine = KtorTestConfig.createMockEngine(
            statusCode = HttpStatusCode.InternalServerError,
            content = "Server Error",
            contentType = ContentType.Text.Plain
        )
        val client = KtorTestConfig.createMockClient(mockEngine)
        val result = client.safeHttpRequest {
            client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
        }
        assertTrue(result is NetworkResult.Error)
        assertEquals(
            "Server error (500): Server error(GET https://android-interview.s3.eu-west-2.amazonaws.com: 500 Internal Server Error. Text: \"Server Error\"",
            (result as NetworkResult.Error).exception.message
        )
    }

    @Test
    fun `safeHttpRequest returns Error on redirect response`() = runTest {
        val mockEngine = KtorTestConfig.createMockEngine(
            statusCode = HttpStatusCode.Found,
            content = "Found",
            contentType = ContentType.Text.Plain,
            headers = mapOf(HttpHeaders.Location to listOf("https://android-interview.s3.eu-west-2.amazonaws.com/redirect"))
        )
        val client = KtorTestConfig.createMockClient(mockEngine)

        val result = client.safeHttpRequest {
            client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
        }
        assertTrue(result is NetworkResult.Error)
        assertEquals(
            "Redirect error (302): Unhandled redirect: GET https://android-interview.s3.eu-west-2.amazonaws.com. Status: 302 Found. Text: \"Found\"",
            (result as NetworkResult.Error).exception.message
        )
    }

    @Test
    fun `safeHttpRequest returns Error on generic exception`() = runTest {
        val exception = IllegalStateException("Test exception")
        val mockEngine = KtorTestConfig.createMockEngine(
            throwException = exception
        )
        val client = KtorTestConfig.createMockClient(mockEngine)
        val result = client.safeHttpRequest {
            client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
        }

        assertTrue(result is NetworkResult.Error)
        assertEquals("Test exception", (result as NetworkResult.Error).exception.message)
    }

    @Test
    fun `safeHttpRequest rethrows CancellationException`() = runTest {

        val exception = CancellationException("Operation cancelled")
        val mockEngine = KtorTestConfig.createMockEngine(
            throwException = exception
        )
        val client = KtorTestConfig.createMockClient(mockEngine)
        assertFailsWith<CancellationException> {
            client.safeHttpRequest {
                client.get("https://android-interview.s3.eu-west-2.amazonaws.com").body<String>()
            }
        }
    }

}