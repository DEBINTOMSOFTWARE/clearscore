package com.dcs.core.data.repository

import com.dcs.core.DataFactory
import com.dcs.core.domain.DomainResult
import com.dcs.core.fakedata.FakeClearScoreService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClearScoreRepositoryImplTest {

    private lateinit var clearScoreRepository: ClearScoreRepositoryImpl
    private lateinit var fakeClearScoreService: FakeClearScoreService

    @Before
    fun setUp() {
        fakeClearScoreService = FakeClearScoreService()
    }

    @Test
    fun `getCreditScore returns Success`() = runTest {
        val expectedDomainModel = DataFactory.expectedDomainModel
        fakeClearScoreService.successResponse = DataFactory.clearScoreApiResponse
        clearScoreRepository = ClearScoreRepositoryImpl(
            clearScoreService = fakeClearScoreService
        )
        val result = clearScoreRepository.getCreditScore()
        assertTrue(result is DomainResult.Success)
        assertEquals(expectedDomainModel, (result as DomainResult.Success).data)
    }

    @Test
    fun `getCreditScore returns Error`() = runTest {
        fakeClearScoreService.successResponse = null
        fakeClearScoreService.errorException = Exception("Network Error")
        clearScoreRepository = ClearScoreRepositoryImpl(
            clearScoreService = fakeClearScoreService
        )
        val result = clearScoreRepository.getCreditScore()
        assertTrue(result is DomainResult.Error)
        assertEquals("Network Error", (result as DomainResult.Error).exception.message)
    }

    @Test(expected = Exception::class)
    fun `getCreditScore throws exception`() = runTest {
        fakeClearScoreService.shouldThrowException = true
        fakeClearScoreService.errorException = Exception("Simulated exception")
        clearScoreRepository = ClearScoreRepositoryImpl(fakeClearScoreService)
        clearScoreRepository.getCreditScore()
    }
}