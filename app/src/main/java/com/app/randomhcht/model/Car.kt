package com.app.randomhcht.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Car(
    var ID: Int,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val slow: Boolean = false,
    val fast: Boolean = false
)
