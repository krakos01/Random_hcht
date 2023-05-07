package com.app.randomhcht.ui

import androidx.lifecycle.ViewModel
import com.app.randomhcht.data.Datasource
import com.app.randomhcht.data.NO_OF_RACES
import com.app.randomhcht.model.Car
import com.app.randomhcht.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var previousP1Cars: MutableList<Car> = mutableListOf()
    private var previousP2Cars: MutableList<Car> = mutableListOf()

    // List of 10 random, shuffled tracks
    private val tracks: List<Track> = Datasource().tracks.asSequence().shuffled().take(NO_OF_RACES).toList()

    init {
        resetApp()
    }

    // Function draws cars for both players.
    // If redraw==true then draws new car until gets one that wasn't used is drawn and returns it.
    // Also handles adding new car to the list of previous cars.
    fun drawCars(
        withRepetitions: Boolean = false,
        redraw: Boolean = false
    ) {
        var car = Datasource().cars.random()
        var car2 = Datasource().cars.random()

        if (!withRepetitions) {
            while (previousP1Cars.contains(car))
                car = Datasource().cars.random()
            while (previousP2Cars.contains(car2))
                car2 = Datasource().cars.random()
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
        _uiState.value = AppUiState(currentRace = 1)
        drawCars()
        //Datasource().swapCarsIds(1,2)
    }


}