package com.example.randomhcht.data

import com.example.randomhcht.R
import com.example.randomhcht.model.Car

class Datasource() {
    fun LoadCars(): List<Car> {
        return listOf(
            Car(1, R.string.Cruiser, R.drawable.cruiser),
            Car(2, R.string.Infinity, R.drawable.infinity),
            Car(3, R.string.Rookie, R.drawable.rookie, slow = true),
            Car(4, R.string.Bliss, R.drawable.bliss),
            Car(5, R.string.Legend, R.drawable.legend),
            Car(6, R.string.Orient, R.drawable.orient),
            Car(7, R.string.Cable_Guy, R.drawable.cable_guy),
            Car(8, R.string.Dynamite, R.drawable.dynamite),
            Car(9, R.string.Specter, R.drawable.specter),
            Car(10, R.string.Dustdriver, R.drawable.dustdriver),
            Car(11, R.string.Loveride, R.drawable.loveride),
            Car(12, R.string.Westbound, R.drawable.westbound),
            Car(13, R.string.Deja_Vu, R.drawable.dejavu),
            Car(14, R.string.Janis, R.drawable.janis),
            Car(15, R.string.Fancy_Grey, R.drawable.fancy_gray),
            Car(16, R.string.Crimson_Chief, R.drawable.crimson_chief),
            Car(17, R.string.Madbot, R.drawable.madbot),
            Car(18, R.string.Haze, R.drawable.haze, fast = true),
            Car(19, R.string.Zeus, R.drawable.zeus, fast = true),
            Car(20, R.string.Macchina, R.drawable.macchina),
            Car(21, R.string.Royal, R.drawable.royal),
            Car(22, R.string.Twilight, R.drawable.twilight, fast = true),
            Car(23, R.string.Fury, R.drawable.fury, fast = true),
            Car(24, R.string.Nano, R.drawable.nano, slow = true),
            Car(25, R.string.Walker_X, R.drawable.walker_x),
            Car(26, R.string.Elite_275, R.drawable.elite275, fast = true),
            Car(27, R.string.Gentleman, R.drawable.gentleman),
            Car(28, R.string.Lightning, R.drawable.lightning),
            Car(29, R.string.Night_Rider, R.drawable.night_rider, fast = true),
            Car(30, R.string.Ladybug, R.drawable.ladybug, slow = true),
            Car(31, R.string.Explorer, R.drawable.explorer),
            Car(32, R.string.The_Fuzz, R.drawable.thefuzz),
            Car(33, R.string.François, R.drawable.francois),
            Car(34, R.string.Minuano, R.drawable.minuano)
        )
    }
}