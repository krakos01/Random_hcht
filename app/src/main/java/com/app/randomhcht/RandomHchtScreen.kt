package com.app.randomhcht

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.randomhcht.ui.AppViewModel
import com.app.randomhcht.ui.DisableCountriesScreen
import com.app.randomhcht.ui.MainScreen
import com.app.randomhcht.ui.OptionsScreen

enum class RandomHchtScreen(@StringRes val title: Int) {
    Start(0),
    Options(1),
    Countries(2)
}

@Composable
fun RandomHchtApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RandomHchtScreen.valueOf(
        backStackEntry?.destination?.route ?: RandomHchtScreen.Start.name
    )

    NavHost(
        navController = navController,
        startDestination = RandomHchtScreen.Start.name
    ) {
        composable(route = RandomHchtScreen.Start.name) {
            MainScreen(
                onOptionsButtonClicked = {
                    navController.navigate(RandomHchtScreen.Options.name)
                }
            )
        }
        composable(route = RandomHchtScreen.Options.name) {
            OptionsScreen(
                onDoneButtonClicked = {
                    navController.popBackStack()
                },
                onDoneButtonClickedAndDifferentNumberOfRaces = {
                    navController.navigate(RandomHchtScreen.Start.name)
                },
                onDisableCountriesClicked = {
                    navController.navigate(RandomHchtScreen.Countries.name)
                }
            )
        }
        composable(route = RandomHchtScreen.Countries.name) {
            DisableCountriesScreen(onDoneButtonClicked = {
                navController.popBackStack()
            })
        }
    }
}

