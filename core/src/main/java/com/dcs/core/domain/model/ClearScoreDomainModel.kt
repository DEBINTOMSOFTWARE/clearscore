package com.dcs.core.domain.model


data class ClearScoreDomainModel(
    val accountIDVStatus: String,
    val creditReportInfo: CreditReportInfoDomainModel,
)

data class CreditReportInfoDomainModel(
    val score: Int,
    val scoreBand: Int,
    val status: String,
    val maxScoreValue: Int,
    val minScoreValue: Int,
    val equifaxScoreBandDescription: String,
)
