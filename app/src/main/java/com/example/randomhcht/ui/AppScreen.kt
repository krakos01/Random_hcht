package com.example.randomhcht.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomhcht.*
import com.example.randomhcht.R
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Country
import com.example.randomhcht.model.Track
import com.example.randomhcht.ui.theme.RandomHchtTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column() {

        DisplayTrackInformation(
            track = appUiState.currentTrack,
            raceNo = appUiState.currentRace
        )
        Spacer(modifier = Modifier.height(10.dp))
        DisplayCars(
            p1Car = appUiState.currentP1Car,
            p2Car = appUiState.currentP2Car
        )
        Spacer(modifier = Modifier.weight(1f))
        DrawAgainAndNextRaceButtons(
            { appViewModel.drawCars(redraw = true) },
            { appViewModel.nextRace() }
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTrackInformation(modifier: Modifier = Modifier, track: Track, raceNo: Int) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(4.dp)
    ) {
        Row(modifier
            .padding(horizontal = 8.dp)
        ) {
            val countryFlag = when(track.Country) {
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
                    .size(40.dp)
                    .padding(end = 8.dp),
                painter = painterResource(countryFlag),
                contentDescription = track.Country.toString(),
            )
            Text(
                modifier = Modifier.align(CenterVertically),
                text = track.IdInCountry.toString() + " - " + stringResource(track.trackName),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.align(CenterVertically),
                text = String.format("| %02d", raceNo) // example: 1 -> 01, 22 -> 22
            )
        }
    }
}


@Composable
fun DisplayCars(
    modifier: Modifier = Modifier,
    p1Car: Car,
    p2Car: Car
) {

    Row(Modifier.padding(horizontal = 4.dp)) {
        CarCard(car = p1Car, modifier = modifier.weight(1f))
        Spacer(modifier = Modifier.width(4.dp))
        CarCard(car = p2Car, modifier = modifier.weight(1f))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarCard(modifier: Modifier = Modifier, car: Car) {
    Card(
        modifier = modifier
    ) {
        Column() {
            Image(painter = painterResource(car.icon), contentDescription = "Car icon")
            Text(
                text = car.ID.toString() + " - " +stringResource(car.name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview() {
    RandomHchtTheme() {
        GameScreen()
    }
}


@Composable
fun DrawAgainAndNextRaceButtons(
    onDrawAgain: () -> Unit,
    onNextRace: () -> Unit
) {
    Row() {
        Button(onClick = onDrawAgain) {
            Text(text = "Draw again")
        }
        Button(onClick =  onNextRace  ) {
            Text(text = "Next race")
        }
    }
}