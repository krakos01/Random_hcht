package com.app.randomhcht.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.randomhcht.model.Car
import com.app.randomhcht.model.Country
import com.app.randomhcht.model.Track
import com.app.randomhcht.ui.AppViewModel
import com.app.randomhcht.ui.theme.RandomHchtTheme
import com.randomhcht.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    RandomHchtTheme {
        MainScreen(onOptionsButtonClicked = {})
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel(),
    onOptionsButtonClicked: () -> Unit = {}
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier.padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        DisplayTrackInformation(
            track = appUiState.currentTrack,
            raceNo = appUiState.currentRace,
            onClick = onOptionsButtonClicked
        )
        Spacer(modifier = Modifier.height(10.dp))
        DisplayCars(
            p1Car = appUiState.currentP1Car,
            p2Car = appUiState.currentP2Car,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.weight(1f))
        DrawAgainAndNextRaceButtons(
            onDrawAgain = { appViewModel.drawCars(redraw = true) },
            onNextRace = { appViewModel.nextRace() },
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )


        if (appUiState.isGameOver) {
            FinishDialog(onPlayAgain = { appViewModel.resetApp() })
        }
    }
}


@Composable
fun DisplayTrackInformation(
    modifier: Modifier = Modifier,
    track: Track,
    raceNo: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(modifier
            .padding(horizontal = 8.dp)
        ) {
            val countryFlag = when(track.country) {
                Country.USA -> R.drawable.usa
                Country.CHILE -> R.drawable.chile
                Country.BRAZIL -> R.drawable.brazil
                Country.SOUTH_AFRICA -> R.drawable.south_africa
                Country.GREECE -> R.drawable.greece
                Country.ICELAND -> R.drawable.iceland
                Country.UAE -> R.drawable.uae
                Country.INDIA -> R.drawable.india
                Country.AUSTRALIA -> R.drawable.australia
                Country.CHINA -> R.drawable.china
                Country.JAPAN -> R.drawable.japan
                Country.HAWAII -> R.drawable.hawaii
            }

            Image(
                modifier = modifier
                    .fillMaxHeight()
                    .size(40.dp)
                    .padding(end = 8.dp)
                    .clickable { onClick() }
                    .testTag("flagButton"),
                painter = painterResource(countryFlag),
                contentDescription = track.country.toString()
            )
            Text(
                modifier = modifier
                    .align(CenterVertically)
                    .testTag(track.trackName.toString()),
                text = track.idInCountry.toString() + " - " + stringResource(track.trackName),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = modifier.weight(1f))
            Text(
                modifier = modifier.align(CenterVertically),
                text = String.format("| %02d", raceNo) // example: 1 -> 01, 22 -> 22
            )
        }
    }
}

@Composable
fun DisplayCars(modifier: Modifier = Modifier, p1Car: Car, p2Car: Car) {
    Row() {
        CarCard(car = p1Car,modifier = modifier)
        Spacer(modifier = Modifier.width(4.dp))
        CarCard(car = p2Car, modifier = modifier)
    }
}


@Composable
fun CarCard(modifier: Modifier = Modifier, car: Car) {
    Card(
        modifier = modifier,
    ) {
        Column {
            Image(
                painter = painterResource(car.icon),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Car icon")
            Text(
                text = car.id.toString() + " - " +stringResource(car.name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun DrawAgainAndNextRaceButtons(onDrawAgain: () -> Unit, onNextRace: () -> Unit, modifier: Modifier = Modifier) {
    Column {
        Button(
            onClick = onDrawAgain,
            modifier = modifier,
            shape = CardDefaults.shape
        ) {
            Text(text = "Draw again")
        }
        Button(
            onClick = onNextRace,
            modifier = modifier,
            shape = CardDefaults.shape
        ) {
            Text(text = "Next race")
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}


@Composable
private fun FinishDialog(
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = "FINISH") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                },

            ) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onPlayAgain,
            ) {
                Text(text = "Play again")
            }
        }
    )
}