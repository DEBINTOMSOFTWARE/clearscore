package com.dcs.core.data.mapper

import com.dcs.core.data.model.ClearScoreApiResponse
import com.dcs.core.data.model.CreditReportInfo
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.model.CreditReportInfoDomainModel

fun ClearScoreApiResponse.toDomainModel() = ClearScoreDomainModel(
    accountIDVStatus = accountIDVStatus,
    creditReportInfo = creditReportInfo.toDomainModel()
)

fun CreditReportInfo.toDomainModel() = CreditReportInfoDomainModel(
    score = score,
    scoreBand = scoreBand ?: 0,
    maxScoreValue = maxScoreValue,
    minScoreValue = minScoreValue,
    equifaxScoreBandDescription = equifaxScoreBandDescription ?: ""
)
