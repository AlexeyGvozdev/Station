package com.example.alexey.station

import android.content.Context
import android.support.v4.content.Loader;
import android.util.Log
import com.example.alexey.station.model.DataAboutStations
import com.example.alexey.station.model.Station
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.util.*


class StationReadLoader(context: Context, private val requestCodeList: Int) : Loader<List<Station>>(context) {

    private var listStations: List<Station>
    private var strJSON: String
    init {
        strJSON = getStringFromAssetFile(context)
        listStations = emptyList()
    }


    override fun onStartLoading() {
        super.onStartLoading()
        when (listStations.isEmpty()) {
            true -> forceLoad()
            false -> deliverResult(listStations)
        }
    }

    override fun onForceLoad() {
        super.onForceLoad()

        // Асинхронное чтение строки json из файла, и парсинг строки json в объект
        Observable.fromCallable { Gson().fromJson(strJSON,DataAboutStations::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { returnListStation(it)},
                        { onErr(it)}
                )
    }

    private fun onErr(err: Throwable?) {
        Log.d("MYTAG", err.toString())
        deliverResult(null)
    }
    /*
    * Метод возвращяет в активити список станций в зависимости от ключа
    * */
    private fun returnListStation(data: DataAboutStations?) {
        val listStations : ArrayList<Station> = ArrayList<Station>()

        if(data == null ) {
            deliverResult(null)
            return
        }

        when (requestCodeList) {
            ListStationActivity.REQUEST_CODE_STATION_FROM -> {
                data.citiesFrom.flatMapTo(listStations) { it.stations }
            }
            ListStationActivity.REQUEST_CODE_STATION_IN -> {
                data.citiesTo.flatMapTo(listStations) { it.stations }
            }
        }
        Collections.sort(listStations, {
            o1,o2 -> compareList(o1, o2)
        })
        deliverResult(listStations)
    }

    /*
    * Метод возвращающий условие сортировки
    * Если страны одинаковые, то будут сравниваться города
    * Иначе сравниваются страны
    * */
    private fun compareList(station1: Station, station2: Station): Int =
            when (station1.countryTitle.equals(station2.countryTitle)) {
                true -> station1.cityTitle.compareTo(station2.cityTitle)
                false -> station1.countryTitle.compareTo(station2.countryTitle)
        }


    /*
    * Читаем данные из файла
    */
    private fun getStringFromAssetFile(context: Context): String {
        val text = "allStations.json"
        var buffer: ByteArray? = null
        val inputStream: InputStream
        try {
            inputStream = context.assets.open(text)
            val size = inputStream.available()
            buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return String(buffer!!)
    }
}