package com.dcs.clearscore

import com.github.tomakehurst.wiremock.client.WireMock

class WireMockStubFactory {

    fun stubCreditScoreSuccess() {
        WireMock.stubFor(
            WireMock.get(WireMock.urlPathMatching("/endpoint"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            """
                            {
                                "accountIDVStatus": "PASS",
                                "creditReportInfo": {
                                    "score": 514,
                                    "scoreBand": 4,
                                    "maxScoreValue": 700,
                                    "minScoreValue": 0
                                },
                                "dashboardStatus": "PASS",
                                "personaType": "INEXPERIENCED"
                            }
                            """.trimIndent()
                        )
                )
        )
    }

    fun stubCreditScoreError() {
        WireMock.stubFor(
            WireMock.get(WireMock.urlPathMatching("/endpoint"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                )
        )
    }
}
