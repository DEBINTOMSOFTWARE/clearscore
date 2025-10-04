package com.dcs.clearscore.presentation.viewmodel

import app.cash.turbine.test
import com.dcs.clearscore.CoroutineTestRule
import com.dcs.clearscore.DataFactory
import com.dcs.clearscore.common.UIState
import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.usecase.ClearScoreUsecase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ClearScoreViewModelTest {

    @get: Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: ClearScoreViewModel
    private val usecase: ClearScoreUsecase = mockk()

    @Test
    fun `creditScore should emit Initial state before getCreditScore is called`() = runTest {
        viewModel = ClearScoreViewModel(usecase, autoLoad = false)
        viewModel.creditScore.test {
            val initialState = awaitItem()
            assertTrue(initialState is UIState.Initial)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCreditScore should emit success states when usecase returns success`() = runTest {
        val mockData = DataFactory.expectedDomainModel
        coEvery { usecase.getCreditScore() } returns DomainResult.Success(mockData)

        viewModel = ClearScoreViewModel(usecase)
        advanceUntilIdle()

        viewModel.creditScore.test {
            // Skip Initial state if present
            var state = awaitItem()
            // Skip Loading state if present
            if (state !is UIState.Success) {
                state = awaitItem()
            }

            assertTrue(state is UIState.Success, "Expected Success state but got $state")
            assertEquals(mockData, state.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCreditScore should emit error state when usecase returns error`() = runTest {
        val errorMessage = "Network error"
        val exception = Exception(errorMessage)
        coEvery { usecase.getCreditScore() } returns DomainResult.Error(exception)

        viewModel = ClearScoreViewModel(usecase)
        advanceUntilIdle()

        viewModel.creditScore.test {
            // Skip Initial state if present
            var state = awaitItem()
            // Skip Loading state if present
            if (state !is UIState.Error) {
                state = awaitItem()
            }

            assertTrue(state is UIState.Error, "Expected Error state but got $state")
            assertEquals(errorMessage, state.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}