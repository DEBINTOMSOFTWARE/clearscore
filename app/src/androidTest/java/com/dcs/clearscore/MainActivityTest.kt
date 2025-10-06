package com.dcs.clearscore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val wireMockServer = WireMockServerHelper()
    private val wireMockStubFactory = WireMockStubFactory()
    private val idlingResource = ComposeIdlingResource.getInstance()

    @Before
    fun setup() {
        wireMockServer.start()
        // Register idling resource
        IdlingRegistry.getInstance().register(idlingResource)
        // Override base URL for tests
        TestUrlProvider.baseUrl = wireMockServer.getBaseUrl()
    }


    @After
    fun tearDown() {
        wireMockServer.stop()
        wireMockServer.resetServer()
        // Unregister idling resource
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun testCreditScoreDisplayed_whenApiReturnsSuccessfully() {
        wireMockStubFactory.stubCreditScoreSuccess()
        composeTestRule.onNodeWithText("514").assertIsDisplayed()
    }

    @Test
    fun testErrorState_whenApiReturnsFail() {
        wireMockStubFactory.stubCreditScoreError()
        composeTestRule.onNodeWithText("Failed to fetch Credit Score").assertIsDisplayed()
    }
}