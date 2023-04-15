package com.randomhcht.ui

import com.randomhcht.data.Datasource
import com.randomhcht.model.Car
import com.randomhcht.model.Track

data class AppUiState(
    val currentRace: Int = 0,
    // todo Change starting cars and track to random ones.
    val currentTrack: Track = Datasource().Tracks[currentRace],
    val currentP1Car: Car = Datasource().Cars[0],
    val currentP2Car: Car = Datasource().Cars[30],
    val isGameOver: Boolean = false
)