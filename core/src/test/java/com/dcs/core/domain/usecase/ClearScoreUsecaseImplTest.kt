package com.dcs.core.domain.usecase

import com.dcs.core.DataFactory
import com.dcs.core.domain.DomainResult
import com.dcs.core.fakedata.FakeClearScoreRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ClearScoreUsecaseImplTest {

    private lateinit var clearScoreUsecaseImpl: ClearScoreUsecaseImpl
    private lateinit var fakeClearScoreRepository: FakeClearScoreRepository

    @Before
    fun setUp() {
        fakeClearScoreRepository = FakeClearScoreRepository()
        clearScoreUsecaseImpl = ClearScoreUsecaseImpl(fakeClearScoreRepository)
    }

    @Test
    fun `getCreditScore returns Success with credit scpre data when repository call succeeds`() = runTest {
            val expectedDomainModel = DataFactory.expectedDomainModel
            fakeClearScoreRepository.setResponse(DomainResult.Success(expectedDomainModel))
            val result = clearScoreUsecaseImpl.getCreditScore()
            assertTrue(result is DomainResult.Success)
            assertEquals(700, expectedDomainModel.creditReportInfo.score)
            assertEquals(1, expectedDomainModel.creditReportInfo.scoreBand)
        }

    @Test
    fun `getCreditScore returns Error when repository call fails`() = runTest {
        val errorMessage = "Failed to retrieve credit score"
        fakeClearScoreRepository.setResponse(DomainResult.Error(Exception(errorMessage)))
        val result = clearScoreUsecaseImpl.getCreditScore()
        assertTrue(result is DomainResult.Error)
        assertEquals(errorMessage, result.exception.message)
    }
}