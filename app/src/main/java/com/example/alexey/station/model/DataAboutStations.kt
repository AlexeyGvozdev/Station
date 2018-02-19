package com.example.alexey.station.model


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class DataAboutStations{
//    @PrimaryKey var id: Int = 0
    var citiesFrom: List<City> = emptyList()
    var citiesTo: List<City> = emptyList()
}
