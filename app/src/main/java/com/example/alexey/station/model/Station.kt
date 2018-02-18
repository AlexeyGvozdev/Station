package com.example.alexey.station.model

import java.io.Serializable

/**
 * Created by alexey on 11.02.18.
 */
data class Station(val countryTitle: String,
               val point: PointClass,
               val districtTitle: String,
               val cityId: Int,
               val cityTitle: String,
               val regionTitle: String,
               val stationId: Int,
               val stationTitle: String) : Serializable

data class PointClass(val longitude: Double, val latitude: Double): Serializable
