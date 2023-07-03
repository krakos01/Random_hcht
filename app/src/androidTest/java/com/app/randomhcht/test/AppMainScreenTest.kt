package com.app.randomhcht.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.app.randomhcht.ui.AppViewModel
import com.app.randomhcht.ui.MainScreen
import org.junit.Rule
import org.junit.Test

class AppMainScreenTest {
    private val viewModel = AppViewModel()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun mainScreen_verifyContent() {
        val currentAppUiState = viewModel.uiState.value

        // When screen is loaded
        composeTestRule.setContent { MainScreen(appViewModel = viewModel) }

        // Track name is displayed
        composeTestRule.onNodeWithTag(currentAppUiState.currentTrack.trackName.toString()).assertIsDisplayed()

        // Cars are displayed
        composeTestRule.onAllNodesWithContentDescription("Car icon")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("Car icon")[1].assertIsDisplayed()
    }
}