package com.dcs.clearscore

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

class WireMockServerHelper {
    private var wireMockServer: WireMockServer? = null

    fun start() {
        wireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
        wireMockServer?.start()

        // Register this as the default for all requests
        WireMock.configureFor("localhost", wireMockServer?.port() ?: 8080)
    }

    fun stop() {
        wireMockServer?.stop()
        wireMockServer = null
    }

    fun resetServer() {
        wireMockServer?.resetAll()
    }

    fun getBaseUrl(): String {
        return "http://localhost:${wireMockServer?.port()}"
    }
}
