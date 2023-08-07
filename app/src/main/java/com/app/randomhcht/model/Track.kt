package com.app.randomhcht.model

import androidx.annotation.StringRes

data class Track(
    val id: Int,
    val idInCountry: Int,
    @StringRes val trackName: Int,
    val country: Country,
)
