package com.app.randomhcht.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.randomhcht.R


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

    Column(modifier = Modifier.padding(5.dp)) {
        /* CARS */
        DisplayCarsOptions()
        Divider()
        Spacer(Modifier.height(5.dp))

        /* TRACKS */
        DisplayTrackOptions(onDisableCountriesClicked)
        Divider()
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
                }
            }
        ) },
    )
}


@Composable
fun DisplayCarsOptions(){
    Text(
        text = stringResource(id = R.string.Cars),
        style = MaterialTheme.typography.headlineSmall
    )

    for (i in 0 until carOptions.count()) {
        carDrawOptions[i]?.let { CarListItem(carOption = carOptions[i], cdOption = it) }
    }
}


@Composable
fun DisplayTrackOptions(
    onDisableCountriesClicked: () -> Unit = {}
) {
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
                if (value.length <= 2) {
                    // todo rename to avoid confusion
                    limitOfTracksFromEachCountry = value.filter { it.isDigit() }
                    limitOfTracksInEachCountry = limitOfTracksFromEachCountry.toInt()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(50.dp)
        ) },
    )

    ListItem(
        headlineContent = {
            Text(
                text = stringResource(id = trackOptions[2]),
                Modifier.clickable { onDisableCountriesClicked() }
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
                        if (value.length in 0..2) {
                            numberOfRaces = value.filter { it.isDigit() }
                            appViewModel.setNumberOfRaces(numberOfRaces.toInt())
                            appViewModel.setPreviousNumberOfRaces(numberOfRaces.toInt())
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangedOptionsWarning(
    onDismissDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissDialog() },
        content = {
            Column {
                Text(text = "Warning", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Changing tracks / races options will reset current progress",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(onClick = onDismissDialog) {
                    Text(text = "OK")
                }
            }
            /*
            Spacer(modifier = Modifier.height(10.dp))
            Text(text =
            "Previous options\n"+
            "Equal number of tracks: $equalNumberOfRacesFromEachCountry" +
            "No more than X tracks: $limitOfTracksInEachCountry" +
            "Disabled countries: " + // todo Show disabled countries
            "Number of races: $NO_OF_RACES"
            ) },
            */
            }
    )
}
