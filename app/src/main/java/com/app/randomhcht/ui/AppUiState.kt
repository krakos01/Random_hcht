package com.app.randomhcht.ui

import com.app.randomhcht.data.Datasource
import com.app.randomhcht.model.Car
import com.app.randomhcht.model.Track

data class AppUiState(
    val currentRace: Int = 0,
    val currentTrack: Track = Datasource.tracks[currentRace],
    val currentP1Car: Car = Datasource.cars[0],
    val currentP2Car: Car = Datasource.cars[30],
    val isGameOver: Boolean = false,
    val numberOfPreviousCars: Int = 0
)