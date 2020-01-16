package com.bruno.pokemonandroid

import android.location.Location

class Pokemon(
    image: Int,
    name: String,
    description: String,
    power: Double,
    lat: Double,
    long: Double
) {
    var name: String? = name
    var description: String? = description
    var image: Int? = image
    var power: Double? = power
    var location: Location? = null
    var isCatch: Boolean? = false

    init {
        this.location = Location(name)
        this.location!!.latitude = lat
        this.location!!.longitude = long
        this.isCatch = false
    }
}