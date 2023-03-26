package com.example.randomhcht.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomhcht.*
import com.example.randomhcht.R
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Country
import com.example.randomhcht.model.Track

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column() {
        // Displays track name
        DisplayTrackInformation(
            track = appUiState.currentTrack
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Displays two cars in a row
        //DisplayCars(
        //    p1Car = appUiState.currentP1Car,
        //    p2Car = appUiState.currentP2Car
        //)

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTrackInformation(modifier: Modifier = Modifier, track: Track) {
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
        }
    }
}

/*
@Composable
fun DisplayCars(
    modifier: Modifier = Modifier,
    p1Car: Car,
    p2Car: Car
) {
    Row(Modifier.padding(horizontal = 4.dp)) {
        CarItem (
            car = p1Car,
            modifier = modifier
                .weight(1f)
                //.clickable { } todo?
        )
        Spacer(modifier = Modifier.width(4.dp))
        CarItem(
            car = p2Car,
            modifier = modifier.weight(1f)
        )
    }
}

 */