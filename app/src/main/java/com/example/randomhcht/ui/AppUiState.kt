package com.example.randomhcht.ui

import com.example.randomhcht.data.Datasource
import com.example.randomhcht.model.Car
import com.example.randomhcht.model.Track

data class AppUiState(
    val currentRace: Int = 1,
    val currentTrack: Track = Datasource().Tracks[1],
    val currentP1Car: Car = Datasource().Cars[1],
    val currentP2Car: Car = Datasource().Cars[1],
)