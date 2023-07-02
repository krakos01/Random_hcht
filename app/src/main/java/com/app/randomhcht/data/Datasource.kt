package com.app.randomhcht.data

import com.example.randomhcht.R
import com.app.randomhcht.model.Car
import com.app.randomhcht.model.Country
import com.app.randomhcht.model.Track



// Track drawing options
var equalNumberOfRacesFromEachCountry = false
var limitOfTracksInEachCountry = 0 // 0 = no limit
var countriesToDisable: List<Country>? = null


object Datasource {

    val trackOptions = listOf (
        R.string.SameNumber,
        R.string.NoMore,
        R.string.DisableCountries,
    )

    val carOptions = listOf(
        Pair(R.string.DisableSlow, "slow"),
        Pair(R.string.DisableFast, "fast"),
        Pair(R.string.DisableNormal, "normal"),
    //  Pair(R.string.DisableSummerCar, "summer")
    )


    val cars: List<Car> = mutableListOf(
        Car(1, R.string.Cruiser, R.drawable.cruiser),
        Car(2, R.string.Infinity, R.drawable.infinity),
        Car(3, R.string.Bliss, R.drawable.bliss),
        Car(4, R.string.Legend, R.drawable.legend),
        Car(5, R.string.Fancy_Grey, R.drawable.fancy_gray),
        Car(6, R.string.Orient, R.drawable.orient),
        Car(7, R.string.Crimson_Chief, R.drawable.crimson_chief),
        Car(8, R.string.Cable_Guy, R.drawable.cable_guy),
        Car(9, R.string.Madbot, R.drawable.madbot),
        Car(10, R.string.Dynamite, R.drawable.dynamite),
        Car(11, R.string.Rookie, R.drawable.rookie, slow = true),
        Car(12, R.string.Specter, R.drawable.specter),
        Car(13, R.string.Haze, R.drawable.haze, fast = true),
        Car(14, R.string.Dustdriver, R.drawable.dustdriver),
        Car(15, R.string.Zeus, R.drawable.zeus, fast = true),
        Car(16, R.string.Macchina, R.drawable.macchina),
        Car(17, R.string.Loveride, R.drawable.loveride),
        Car(18, R.string.Royal, R.drawable.royal),
        Car(19, R.string.Westbound, R.drawable.westbound),
        Car(20, R.string.Twilight, R.drawable.twilight, fast = true),
        Car(21, R.string.Deja_Vu, R.drawable.dejavu),
        Car(22, R.string.Fury, R.drawable.fury, fast = true),
        Car(23, R.string.Janis, R.drawable.janis),
        Car(24, R.string.Nano, R.drawable.nano, slow = true),
        Car(25, R.string.Walker_X, R.drawable.walker_x),
        Car(26, R.string.Gentleman, R.drawable.gentleman),
        Car(27, R.string.Elite_275, R.drawable.elite275, fast = true),
        Car(28, R.string.Lightning, R.drawable.lightning),
        Car(29, R.string.Night_Rider, R.drawable.night_rider, fast = true),
        Car(30, R.string.François, R.drawable.francois),
        Car(31, R.string.Ladybug, R.drawable.ladybug, slow = true),
        Car(32, R.string.Explorer, R.drawable.explorer),
        Car(33, R.string.Minuano, R.drawable.minuano),
        Car(34, R.string.The_Fuzz, R.drawable.thefuzz),
        // TODO Add 35th car from summer addon
    )

    fun swapCarsIds(car: Car, newId: Int) {
        if (newId in 0..35) {
            val oldId = car.ID
            car.ID = newId
            cars.find { it.ID == newId }!!.ID = oldId //todo
        }
    }

    val tracks: List<Track> = listOf(
        Track(1,1,R.string.Grass_Hills, Country.USA),
        Track(2,2,R.string.Rocky_Road, Country.USA),
        Track(3,3,R.string.Sunset, Country.USA),
        Track(4,4,R.string.Morning_Walk, Country.USA),
        Track(5,5,R.string.Into_the_Woods, Country.USA),
        Track(6,6,R.string.Big_Orange, Country.USA),
        Track(7,7,R.string.Night_of_Angels, Country.USA),
        Track(8,8,R.string.Asphalt_and_Sunshine, Country.USA),
        Track(9,9,R.string.Skulls_and_Spikes, Country.USA),
        Track(1,1,R.string.Arid, Country.CHILE),
        Track(11,2,R.string.Cemetery, Country.CHILE),
        Track(12,3,R.string.Alma, Country.CHILE),
        Track(13,4,R.string.La_Capital, Country.CHILE),
        Track(14,5,R.string.Snow_Flurry, Country.CHILE),
        Track(15,6,R.string.Cold_Day, Country.CHILE),
        Track(16,7,R.string.Rollercoaster, Country.CHILE),
        Track(17,8,R.string.Country_Life, Country.CHILE),
        Track(18,9,R.string.Moonlight, Country.CHILE),
        Track(19,10,R.string.Moai, Country.CHILE),
        Track(20,1,R.string.I_Love_Brasilia, Country.BRAZIL),
        Track(21,2,R.string.Balloon_Festival, Country.BRAZIL),
        Track(22,3,R.string.Big_Sky, Country.BRAZIL),
        Track(23,4,R.string.The_Guaíba_Sunset, Country.BRAZIL),
        Track(24,5,R.string.Nightfall_in_Porto_Alegre, Country.BRAZIL),
        Track(25,6,R.string.Rain, Country.BRAZIL),
        Track(26,7,R.string.Tropical, Country.BRAZIL),
        Track(27,8,R.string.Seaside, Country.BRAZIL),
        Track(28,9,R.string.Historic_Center, Country.BRAZIL),
        Track(29,10,R.string.Circus, Country.BRAZIL),
        Track(30,11,R.string.A_New_Day, Country.BRAZIL),
        Track(31,12,R.string.Plateau, Country.BRAZIL),
        Track(32,1,R.string.Overseas, Country.SOUTH_AFRICA),
        Track(33,2,R.string.Peninsula, Country.SOUTH_AFRICA),
        Track(34,3,R.string.Rite_of_Passage, Country.SOUTH_AFRICA),
        Track(35,4,R.string.Monumental, Country.SOUTH_AFRICA),
        Track(36,5,R.string.The_Gardens, Country.SOUTH_AFRICA),
        Track(37,6,R.string.Zig_Zag, Country.SOUTH_AFRICA),
        Track(38,7,R.string.Wild_Night, Country.SOUTH_AFRICA),
        Track(39,8,R.string.Pitch_Black, Country.SOUTH_AFRICA),
        Track(40,9,R.string.Baobabs, Country.SOUTH_AFRICA),
        Track(41,1,R.string.Lithos, Country.GREECE),
        Track(42,2,R.string.Tunnel_Road, Country.GREECE),
        Track(43,3,R.string.Nightfall, Country.GREECE),
        Track(44,4,R.string.Thunderstorm, Country.GREECE),
        Track(45,5,R.string.Denouement, Country.GREECE),
        Track(46,6,R.string.Serenity, Country.GREECE),
        Track(47,7,R.string.Portuary, Country.GREECE),
        Track(48,8,R.string.Purple_Sky, Country.GREECE),
        Track(49,9,R.string.Starry_Night, Country.GREECE),
        Track(50,10,R.string.Boka, Country.GREECE),
        Track(51,1,R.string.Spring_Fields, Country.ICELAND),
        Track(52,2,R.string.Snowfall, Country.ICELAND),
        Track(53,3,R.string.Aurora_Borealis, Country.ICELAND),
        Track(54,4,R.string.Clear_As_Night, Country.ICELAND),
        Track(55,5,R.string.Playpen, Country.ICELAND),
        Track(56,6,R.string.Blizzard, Country.ICELAND),
        Track(57,7,R.string.Jörmungandr, Country.ICELAND),
        Track(58,8,R.string.Snow_Pass, Country.ICELAND),
        Track(59,1,R.string.Slithering_Sands, Country.UAE),
        Track(60,2,R.string.Haboob, Country.UAE),
        Track(61,3,R.string.Tough_Choices, Country.UAE),
        Track(62,4,R.string.One_Lap, Country.UAE),
        Track(63,5,R.string.Abundance, Country.UAE),
        Track(64,6,R.string.Arabian_Nights, Country.UAE),
        Track(65,7,R.string.Sandstorm, Country.UAE),
        Track(66,8,R.string.Dunes, Country.UAE),
        Track(67,1,R.string.Monsoon, Country.INDIA),
        Track(68,2,R.string.Golden_Path, Country.INDIA),
        Track(69,3,R.string.Royal_Sunset, Country.INDIA),
        Track(70,4,R.string.Majestic, Country.INDIA),
        Track(71,5,R.string.Cycles, Country.INDIA),
        Track(72,6,R.string.Older_Than_Time, Country.INDIA),
        Track(73,7,R.string.Downpour, Country.INDIA),
        Track(74,8,R.string.Wrath_of_Gods, Country.INDIA),
        Track(75,9,R.string.Twist_and_Shout, Country.INDIA),
        Track(76,1,R.string.Sunny_Day, Country.AUSTRALIA),
        Track(77,2,R.string.Sea_Breeze, Country.AUSTRALIA),
        Track(78,3,R.string.Getting_Cold, Country.AUSTRALIA),
        Track(79,4,R.string.Big_Rock, Country.AUSTRALIA),
        Track(80,5,R.string.Stardust, Country.AUSTRALIA),
        Track(81,6,R.string.Comb, Country.AUSTRALIA),
        Track(82,7,R.string.Twisted, Country.AUSTRALIA),
        Track(83,8,R.string.City_Lights_Track, Country.AUSTRALIA),
        Track(84,9,R.string.Beach, Country.AUSTRALIA),
        Track(85,1,R.string.Market, Country.CHINA),
        Track(86,2,R.string.Pandas, Country.CHINA),
        Track(87,3,R.string.Night_Life, Country.CHINA),
        Track(88,4,R.string.Line_of_Defense, Country.CHINA),
        Track(89,5,R.string.Restored, Country.CHINA),
        Track(90,6,R.string.Megalopolis, Country.CHINA),
        Track(91,7,R.string.Might, Country.CHINA),
        Track(92,8,R.string.Midnight, Country.CHINA),
        Track(93,9,R.string.New_Year, Country.CHINA),
        Track(94,1,R.string.Sunrise, Country.JAPAN),
        Track(95,2,R.string.Kawaii, Country.JAPAN),
        Track(96,3,R.string.Electric_Town, Country.JAPAN),
        Track(97,4,R.string.Spiral, Country.JAPAN),
        Track(98,5,R.string.Nature, Country.JAPAN),
        Track(99,6,R.string.Red, Country.JAPAN),
        Track(100,7,R.string.Oni, Country.JAPAN),
        Track(101,8,R.string.Secluded, Country.JAPAN),
        Track(102,9,R.string.Snow_Festival, Country.JAPAN),
        Track(103,1,R.string.Paradise, Country.HAWAII),
        Track(104,2,R.string.Crescent, Country.HAWAII),
        Track(105,3,R.string.Insulated, Country.HAWAII),
        Track(106,4,R.string.Idyllic, Country.HAWAII),
        Track(107,5,R.string.Rose_tinted_Sky, Country.HAWAII),
        Track(108,6,R.string.Aloha, Country.HAWAII),
        Track(109,7,R.string.Toxic, Country.HAWAII),
        Track(110,8,R.string.Ashes, Country.HAWAII),
        Track(111,9,R.string.Final_Challenge, Country.HAWAII),
    )
}