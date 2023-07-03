package com.app.randomhcht.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.app.randomhcht.RandomHchtApp
import com.app.randomhcht.RandomHchtScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            RandomHchtApp(navController=navController) }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(RandomHchtScreen.Start.name)
    }

    @Test
    fun appNavHost_clickFlag_navigateToOptionsScreen() {
        composeTestRule.onNodeWithTag("flagButton").performClick()
        navController.assertCurrentRouteName(RandomHchtScreen.Options.name)
    }

    @Test
    fun appNavHost_clickDoneButton_navigateToMainScreen() {
        composeTestRule.onNodeWithTag("flagButton").performClick()
        composeTestRule.onNodeWithText("Done").performClick()
        navController.assertCurrentRouteName(RandomHchtScreen.Start.name)
    }
}