package com.example.alexey.station.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import com.example.alexey.station.ListStationActivity.Companion.KEY_STATION
import com.example.alexey.station.R
import com.example.alexey.station.model.Station


class InformationDialogAboutStation: DialogFragment() {

    /*
    * Получаем данные об объекте и заполняем поля
    * */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)


        val station: Station? = arguments.getSerializable(KEY_STATION) as? Station
        val view: View = activity.layoutInflater.inflate(R.layout.info_about_station, null)

        builder.setView(view)
                .setPositiveButton("ОК", DialogInterface.OnClickListener {
                    dialog, id ->
                    // Закрываем окно
                    dialog.cancel()
                })
        view.findViewById<TextView>(R.id.tv_info_name_station).text = station?.stationTitle
        view.findViewById<TextView>(R.id.tv_info_country).text = station?.countryTitle
        view.findViewById<TextView>(R.id.tv_info_city).text = station?.cityTitle
        if(station?.regionTitle!!.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tv_info_region).text = station.regionTitle
        } else {
            view.findViewById<TextView>(R.id.tv_info_base_region).visibility = View.GONE
            view.findViewById<TextView>(R.id.tv_info_region).visibility = View.GONE
        }

        return builder.create()

    }

}