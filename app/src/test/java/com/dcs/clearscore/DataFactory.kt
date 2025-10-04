package com.dcs.clearscore

import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.model.CreditReportInfoDomainModel

object DataFactory {

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