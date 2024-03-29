package com.app.randomhcht

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.randomhcht.ui.AppViewModel
import com.app.randomhcht.ui.screens.ChangeCarIdScreen
import com.app.randomhcht.ui.screens.MainScreen
import com.app.randomhcht.ui.screens.OptionsScreen

enum class RandomHchtScreen(@StringRes val title: Int) {
    Start(0),
    Options(1),
    CarsIdChange(2)
}

@Composable
fun RandomHchtApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

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
                    navController.navigate(RandomHchtScreen.CarsIdChange.name)
                }
            )
        }
        composable(route = RandomHchtScreen.CarsIdChange.name) {
            ChangeCarIdScreen(
                onDoneButtonClicked = { navController.popBackStack() },
                carsList = viewModel.myCars
            )
        }
    }
}
