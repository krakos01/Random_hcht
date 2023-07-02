package com.app.randomhcht.ui

import androidx.lifecycle.ViewModel
import com.app.randomhcht.data.Datasource
import com.app.randomhcht.data.Datasource.cars
import com.app.randomhcht.data.equalNumberOfRacesFromEachCountry
import com.app.randomhcht.data.limitOfTracksInEachCountry
import com.app.randomhcht.model.Car
import com.app.randomhcht.model.Country
import com.app.randomhcht.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil

private var NO_OF_RACES = 10
private var previousNO_OF_RACES: Int = NO_OF_RACES

// 0 -> Disable Slow
// 1 -> Disable Fast
// 2 -> Disable Normal
val carDrawOptions = mutableMapOf(0 to false, 1 to false, 2 to false)

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var previousP1Cars: MutableList<Car> = mutableListOf()
    private var previousP2Cars: MutableList<Car> = mutableListOf()

    // List of 10 random, shuffled tracks
    var tracks: List<Track> = mutableListOf()


    init {
        resetApp()
    }

    fun setNumberOfRaces(newNoOfRaces: Int) {
        NO_OF_RACES = newNoOfRaces
    }

    fun getNumberOfRaces(): Int {
        return NO_OF_RACES
    }

    fun setPreviousNumberOfRaces(newNoOfRaces: Int) {
        previousNO_OF_RACES = newNoOfRaces
    }

    fun getPreviousNumberOfRaces(): Int {
        return previousNO_OF_RACES
    }


    // Function draws cars for both players.
    // If redraw==true then draws new car until gets one that wasn't used is drawn and returns it.
    // Also handles adding new car to the list of previous cars.
    fun drawCars(
        withRepetitions: Boolean = false,
        redraw: Boolean = false
    ) {

        var carsBank = cars
        if (carDrawOptions[0] == true) carsBank = carsBank.filter { !it.slow }
        if (carDrawOptions[1] == true) carsBank = carsBank.filter { !it.fast }
        if (carDrawOptions[2] == true) carsBank = carsBank.filter { it.fast || it.slow }


        var car = carsBank.random()
        var car2 = carsBank.random()

        if (!withRepetitions && carsBank.size>=NO_OF_RACES) {
            while (previousP1Cars.contains(car))
                car = carsBank.random()
            while (previousP2Cars.contains(car2))
                car2 = carsBank.random()
        }
        else {
            car=carsBank.random()
            car2=carsBank.random()
        }

        if (!redraw) {
            previousP1Cars.add(car)
            previousP2Cars.add(car2)
        }
        _uiState.update { currentState ->
            currentState.copy(
                currentP1Car = car,
                currentP2Car = car2
            )
        }
    }


    private fun drawTracks() {
        if (equalNumberOfRacesFromEachCountry) {
            limitOfTracksInEachCountry = (ceil(NO_OF_RACES.toDouble() / Country.values().size.toDouble())).toInt()
        }

        if (limitOfTracksInEachCountry == 0) {
            tracks = Datasource.tracks.asSequence().shuffled().take(NO_OF_RACES).toList()
        }
        else {
            val listOfPreviousCountries: MutableList<Country> = mutableListOf()
            val listOfTracks: MutableList<Track> = mutableListOf()
            for (i in 1..NO_OF_RACES) {
                var elem = Datasource.tracks.random()
                while (listOfPreviousCountries.count { it == elem.Country } >= limitOfTracksInEachCountry) {
                    elem = Datasource.tracks.random()
                }
                listOfPreviousCountries.add(elem.Country)
                listOfTracks.add(elem)
            }

            tracks = listOfTracks
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentTrack = tracks[0]
            )
        }
    }


    fun nextRace() {
        if (_uiState.value.numberOfPreviousCars == NO_OF_RACES-1) {
            _uiState.update { currentState ->
                currentState.copy(isGameOver = true)

            }
            // Log.i("nextRacesLog","Finished with "+_uiState.value.isGameOver)
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    currentRace = currentState.currentRace.inc(),
                    currentTrack = tracks[currentState.currentRace],
                    numberOfPreviousCars = currentState.numberOfPreviousCars.inc()
                )
            }
            drawCars()
        }
    }


    fun resetApp() {
        previousP1Cars.clear()
        previousP2Cars.clear()
        _uiState.value = AppUiState(currentRace = 1, numberOfPreviousCars = 0)
        drawCars()
        drawTracks()
        //Datasource().swapCarsIds(1,2)
    }
}