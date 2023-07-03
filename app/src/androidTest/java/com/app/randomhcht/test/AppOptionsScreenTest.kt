package com.app.randomhcht.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.app.randomhcht.data.Datasource
import com.app.randomhcht.ui.OptionsScreen
import com.example.randomhcht.R
import org.junit.Rule
import org.junit.Test

class AppOptionsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * When OptionsScreen is loaded, the car, track and race options are displayed.
     */
    @Test
    fun optionsScreen_verifyContent() {
        // When screen is loaded
        composeTestRule.setContent {
            OptionsScreen()
        }

        // Then all the options are displayed
        Datasource.trackOptions.forEach {
            composeTestRule.onNodeWithStringId(it).assertIsDisplayed()
        }
        Datasource.carOptions.forEach {
            composeTestRule.onNodeWithStringId(it.first).assertIsDisplayed()
        }
        composeTestRule.onNodeWithStringId(R.string.NumberOfRaces).assertIsDisplayed()
    }

}