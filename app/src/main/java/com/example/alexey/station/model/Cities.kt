package com.example.alexey.station.model

/**
 * Created by alexey on 11.02.18.
 */
data class Cities(val countryTitle: String,
             val point: PointClass,
             val districtTitle: String,
             val cityId: Int,
             val cityTitle: String,
             val regionTitle: String,
             val stations: List<Station>
)