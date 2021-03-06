package com.example.alexey.station.model


data class City(val countryTitle: String,
             val point: PointClass,
             val districtTitle: String,
             val cityId: Int,
             val cityTitle: String,
             val regionTitle: String,
             val stations: List<Station>
)