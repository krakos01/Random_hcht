package com.randomhcht.model

import androidx.annotation.StringRes

data class Track(
    val ID: Int,
    val IdInCountry: Int,
    @StringRes val trackName: Int,
    val Country: Country,
)
