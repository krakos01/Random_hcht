package com.example.randomhcht

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomhcht.data.Datasource
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Track
import com.example.randomhcht.ui.theme.RandomHchtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomHchtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomHchtApp()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RandomHchtApp() {
    RandomHchtTheme {
        Tournament()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarItem(modifier: Modifier = Modifier, car: Car) {
    Card(modifier = Modifier) {
        Column() {
            Image(
                painter = painterResource(car.icon),
                contentDescription = stringResource(car.name),
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .padding(vertical = 4.dp)
            )
            Text(
                text = car.ID.toString() + " - " + stringResource(car.name),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(CenterHorizontally))
        }
    }
}


@Composable
fun Tournament(modifier: Modifier = Modifier) {
    val tracks = drawTracksNormally(2)
    val raceID = 1
    val playerOneCars: MutableList<Car> = mutableListOf(Car(1,R.string.Cruiser,R.drawable.cruiser))
    val playerTwoCars: MutableList<Car> = mutableListOf(Car(1,R.string.Cruiser,R.drawable.cruiser))

    Column() {
        // Displays track name
        Text(
            text = stringResource(tracks[raceID].trackName),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Displays two cars in a row
        DrawCarsNormally(p1Cars = playerOneCars, p2Cars = playerTwoCars)

    }
}

@Composable
fun DrawCarsNormally(
    modifier: Modifier = Modifier,
    p1Cars: MutableList<Car>,
    p2Cars: MutableList<Car>,
    slow: Boolean = false,
    fast: Boolean = false
)  {

    var newCar1 by remember { mutableStateOf(p1Cars.last()) }
    var newCar2 by remember { mutableStateOf(p2Cars.last()) }

    Row(Modifier.padding(horizontal = 4.dp)) {
        CarItem (
            car = newCar1,
            modifier = modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(4.dp))
        CarItem(
            car = newCar2,
            modifier = modifier.weight(1f)
        )
    }

    // Button to Tournament composable?
    Button(
        onClick = {
            p1Cars.add(Datasource().LoadCars().random())
            newCar1 = p1Cars.last()
            p2Cars.add(Datasource().LoadCars().random())
            newCar2 = p2Cars.last()

        }) {
        Text(text = "Click")
    }
}


// Normally - random tracks from random countries
@Composable
fun drawTracksNormally(numberOfRaces: Int) : List<Track>{
    return Datasource().LoadTracks().asSequence().shuffled().take(numberOfRaces).toList()
}