package com.app.randomhcht.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.randomhcht.data.Datasource.carOptions
import com.app.randomhcht.data.Datasource.trackOptions
import com.app.randomhcht.data.equalNumberOfRacesFromEachCountry
import com.app.randomhcht.data.limitOfTracksInEachCountry
import com.app.randomhcht.ui.AppViewModel
import com.app.randomhcht.ui.carDrawOptions
import com.randomhcht.R


@Preview(showSystemUi = true)
@Composable
fun OptionsPreview() {
    OptionsScreen()
}

@Composable
fun OptionsScreen(
    appViewModel: AppViewModel = viewModel(),
    onDoneButtonClicked: () -> Unit = {},
    onDoneButtonClickedAndDifferentNumberOfRaces: () -> Unit = {},
    onDisableCountriesClicked: () -> Unit = {},
) {
    val showDialog = remember { mutableStateOf(false) }
    val hideDialog = { showDialog.value = false}
    val previousNumberOfRaces = appViewModel.getPreviousNumberOfRaces()

    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        /* CARS */
        DisplayCarsOptions(onDisableCountriesClicked)
        HorizontalDivider()
        Spacer(Modifier.height(5.dp))

        /* TRACKS */
        DisplayTrackOptions()
        HorizontalDivider()
        Spacer(Modifier.height(5.dp))

        /* RACES */
        DisplayRaceOptions(
            onFocusChanged = { showDialog.value = true },
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.height(10.dp))

        /* DONE BUTTON */
        Button(onClick = {
            if (previousNumberOfRaces != appViewModel.getNumberOfRaces()) {
                onDoneButtonClickedAndDifferentNumberOfRaces()
            } else
                onDoneButtonClicked()
        }) {Text(text = "Done") }

        /* DIALOG */
        if (showDialog.value) ChangedOptionsWarning(hideDialog)
    }
}


@Composable
fun CarListItem(carOption: Pair<Int,String>, cdOption: Boolean) {
    var rememberedOption by remember { mutableStateOf(cdOption) }

    ListItem(
        headlineContent = { Text(text = stringResource(id = carOption.first)) },
        trailingContent = { Switch(
            checked = rememberedOption,
            onCheckedChange = {
                rememberedOption = it
                when(carOption.second) {
                    "slow" -> carDrawOptions[0] = it
                    "fast" -> carDrawOptions[1] = it
                    "normal" -> carDrawOptions[2] = it
                    "summer" -> carDrawOptions[3] = it
                }
            }
        ) },
    )
}


@Composable
fun DisplayCarsOptions(
    onDisableCountriesClicked: () -> Unit
){
    Text(
        text = stringResource(id = R.string.Cars),
        style = MaterialTheme.typography.headlineSmall
    )

    for (i in 0 until carOptions.count()) {
        carDrawOptions[i]?.let { CarListItem(carOption = carOptions[i], cdOption = it) }
    }

    ListItem(
        headlineContent = {
            Text(
                text = stringResource(id = carOptions[4].first),
                Modifier.clickable { onDisableCountriesClicked() }
            ) },
    )
}


@Composable
fun DisplayTrackOptions() {
    var equalNumberOfRaces by remember { mutableStateOf(equalNumberOfRacesFromEachCountry) }
    var limitOfTracksFromEachCountry by remember { mutableStateOf(limitOfTracksInEachCountry.toString()) }
    // var disabledCountries = remember { mutableListOf<Country>() }

    Text(
        text = stringResource(id = R.string.Tracks),
        style = MaterialTheme.typography.headlineSmall
    )

    ListItem(
        headlineContent = { Text(text = stringResource(id = trackOptions[0])) },
        trailingContent = {
            Switch(
                checked = equalNumberOfRaces,
                onCheckedChange = {
                  //  onOptionChanged()
                    equalNumberOfRaces = it
                    equalNumberOfRacesFromEachCountry = it
                }
            )

        })

    ListItem(
        headlineContent = { Text(text = stringResource(id = trackOptions[1])) },
        trailingContent = { TextField(
            value = limitOfTracksFromEachCountry,
            onValueChange = { value ->
                if (value.length in 1..2) {
                    limitOfTracksFromEachCountry = value.filter { it.isDigit() }
                    limitOfTracksInEachCountry = limitOfTracksFromEachCountry.toInt()
                }
                else if (value.isEmpty()) {
                    limitOfTracksFromEachCountry = ""
                    limitOfTracksInEachCountry = 0
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(50.dp)
        ) },
    )
}


@Composable
fun DisplayRaceOptions(
    onFocusChanged: () -> Unit,
    appViewModel: AppViewModel
) {
    var numberOfRaces by remember { mutableStateOf(appViewModel.getNumberOfRaces().toString()) }

    Column {
        Text(
            text = stringResource(id = R.string.Races),
            style = MaterialTheme.typography.headlineSmall
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.NumberOfRaces)) },
            trailingContent = {
                TextField(
                    value = numberOfRaces,
                    onValueChange = { value ->
                        if (value.length in 1..2) {
                            numberOfRaces = value.filter { it.isDigit() }
                            appViewModel.setNumberOfRaces(numberOfRaces.toInt())
                            appViewModel.setPreviousNumberOfRaces(numberOfRaces.toInt())
                        }
                        else if (value.isEmpty()) {
                            numberOfRaces = ""
                            appViewModel.setNumberOfRaces()
                            appViewModel.setPreviousNumberOfRaces()
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .width(50.dp)
                        .onFocusChanged {
                            if (it.isFocused) onFocusChanged()
                        }
                )
            },
        )
    }
}

@Composable
fun ChangedOptionsWarning(
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onDismissDialog() },
        title = { Text(text = "Be careful!") },
        text = { Text(text = "Changing this will reset current progress") },
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = onDismissDialog,
            ) {
                Text(text = "OK")
            }
        }
    )
}
