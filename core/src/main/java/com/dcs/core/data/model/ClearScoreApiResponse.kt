package com.dcs.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClearScoreApiResponse(
    val accountIDVStatus: String,
    val creditReportInfo: CreditReportInfo,
)

@Serializable
data class CreditReportInfo(
    val score: Int,
    val scoreBand: Int? = null,
    val status: String,
    val maxScoreValue: Int,
    val minScoreValue: Int,
    val equifaxScoreBandDescription: String? = null,
)