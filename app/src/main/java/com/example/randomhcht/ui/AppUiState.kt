package com.example.randomhcht.ui

import com.example.randomhcht.data.Datasource
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Track

data class AppUiState(
    val currentRace: Int = 0,
    // todo Change starting cars and track to random ones.
    val currentTrack: Track = Datasource().Tracks[currentRace],
    val currentP1Car: Car = Datasource().Cars[0],
    val currentP2Car: Car = Datasource().Cars[30],
    val isGameOver: Boolean = false
)