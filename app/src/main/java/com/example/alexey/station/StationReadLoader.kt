package com.example.alexey.station

import android.content.Context
import android.support.v4.content.Loader;
import android.util.Log
import android.view.View
import com.example.alexey.station.model.DataAboutStations
import com.example.alexey.station.model.Station
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_list_station.*
import java.io.IOException
import java.io.InputStream


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

        Observable.fromCallable { Gson().fromJson(strJSON,DataAboutStations::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fillRecyclerView(it)},
                        { onErr(it)}
                )
    }

    private fun onErr(err: Throwable?) {
        Log.d("MYTAG", err.toString())
        deliverResult(null)
    }

    private fun fillRecyclerView(data: DataAboutStations?) {
        val listStations : ArrayList<Station> = ArrayList<Station>()
        Log.d("MYTAG", requestCodeList.toString())
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
        deliverResult(listStations)
    }

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