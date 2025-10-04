package com.dcs.core

import com.dcs.core.data.model.ClearScoreApiResponse
import com.dcs.core.data.model.CreditReportInfo
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.model.CreditReportInfoDomainModel

object DataFactory {
    val creditReportInfo = CreditReportInfo(
        score = 700,
        scoreBand = 1,
        status = "active",
        maxScoreValue = 800,
        minScoreValue = 0,
        equifaxScoreBandDescription = "equifaxScoreBandDescription"
    )

    val clearScoreApiResponse = ClearScoreApiResponse(
        accountIDVStatus = "accountIDVStatus",
        creditReportInfo = creditReportInfo
    )

    val expectedCreditReportInfoDomainModel = CreditReportInfoDomainModel(
        score = 700,
        scoreBand = 1,
        status = "active",
        maxScoreValue = 800,
        minScoreValue = 0,
        equifaxScoreBandDescription = "equifaxScoreBandDescription"
    )

    val expectedDomainModel = ClearScoreDomainModel(
        accountIDVStatus = "accountIDVStatus",
        creditReportInfo = expectedCreditReportInfoDomainModel

    )

}