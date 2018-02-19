package com.example.alexey.station.model

import java.io.Serializable


data class Station(val countryTitle: String,
               val point: PointClass,
               val districtTitle: String,
               val cityId: Int,
               val cityTitle: String,
               val regionTitle: String,
               val stationId: Int,
               val stationTitle: String) : Serializable

