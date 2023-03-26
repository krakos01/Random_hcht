package com.example.randomhcht.ui

import androidx.lifecycle.ViewModel
import com.example.randomhcht.data.Datasource
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private lateinit var usedP1Cars: MutableList<Car>
    private lateinit var usedP2Cars: MutableList<Car>


    // List of 10 random, shuffled tracks
    val tracks: List<Track> = Datasource().Tracks.asSequence().shuffled().take(10).toList()


    init {
        usedP1Cars.clear()
        usedP2Cars.clear()
        _uiState.value = AppUiState(currentRace = 1)
        drawCars()
    }


    // Function draws cars for both players.
    // If redraw==true then draws new car until gets one that wasn't used is drawn and returns it.
    // Also handles adding new car to the list of previous cars.
    fun drawCars(
        withRepetitions: Boolean = false,
        redraw: Boolean = false
    ) {
        if (redraw && usedP1Cars.isNotEmpty()) usedP1Cars.removeLast()
        if (redraw && usedP2Cars.isNotEmpty()) usedP2Cars.removeLast()

        var car = Datasource().Cars.random()
        var car2 = Datasource().Cars.random()

        if(!withRepetitions) {
            while(usedP1Cars.contains(car))
                car = Datasource().Cars.random()
            while(usedP2Cars.contains(car2))
                car2 = Datasource().Cars.random()
        }

        _uiState.update { _uiState.value.copy(currentP1Car = car, currentP2Car = car2)}
        usedP1Cars.add(car)
        usedP2Cars.add(car2)
    }








}