package com.example.randomhcht.ui

import androidx.lifecycle.ViewModel
import com.example.randomhcht.data.Datasource
import com.example.randomhcht.data.NO_OF_RACES
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Track
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
    private val tracks: List<Track> = Datasource().Tracks.asSequence().shuffled().take(NO_OF_RACES).toList()



    // Function draws cars for both players.
    // If redraw==true then draws new car until gets one that wasn't used is drawn and returns it.
    // Also handles adding new car to the list of previous cars.
    fun drawCars(
        withRepetitions: Boolean = false,
        redraw: Boolean = false
    ) {
        // if (redraw && previousP1Cars.isNotEmpty()) previousP1Cars.removeLast()
        // if (redraw && previousP2Cars.isNotEmpty()) previousP2Cars.removeLast()

        var car = Datasource().Cars.random()
        var car2 = Datasource().Cars.random()

        if (!withRepetitions) {
            while (previousP1Cars.contains(car))
                car = Datasource().Cars.random()
            while (previousP2Cars.contains(car2))
                car2 = Datasource().Cars.random()
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
        if (previousP1Cars.size == NO_OF_RACES) {

        }
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    currentRace = currentState.currentRace.inc(),
                    currentTrack = tracks[currentState.currentRace]
                )
            }

            drawCars()
        }
    }



}