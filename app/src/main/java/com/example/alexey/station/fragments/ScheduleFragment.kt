package com.example.alexey.station.fragments


import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alexey.station.ListStationActivity
import com.example.alexey.station.ListStationActivity.Companion.SELECTED_STATION
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_FROM
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_IN
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STRING

import com.example.alexey.station.R
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment() {




    val TAG = "MYTAG"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_schedule, container, false)


        val btn_choose_station_from = view.findViewById<View>(R.id.btn_choose_station_from)
        val btn_choose_station_in = view.findViewById<View>(R.id.btn_choose_station_in)
        btn_choose_station_from.setOnClickListener( { openListStationActivity(REQUEST_CODE_STATION_FROM) } )
        btn_choose_station_in.setOnClickListener( { openListStationActivity(REQUEST_CODE_STATION_IN) } )

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        val selectedStation: String
        if (resultCode == Activity.RESULT_OK) {
            selectedStation = intent?.getStringExtra(SELECTED_STATION) ?: ""

            if (!selectedStation.isEmpty()) {
                when (requestCode) {
                    REQUEST_CODE_STATION_FROM -> {
                        tv_station_from.text = selectedStation
                        tv_station_from.visibility = View.VISIBLE
                    }
                    REQUEST_CODE_STATION_IN -> {
                        tv_station_in.text = selectedStation
                        tv_station_in.visibility = View.VISIBLE
                    }
                }
            }
        }



    }

    private fun openListStationActivity(requestCode: Int) {
        val intent = Intent(activity, ListStationActivity::class.java)
        intent.putExtra(REQUEST_CODE_STRING, requestCode)
        startActivityForResult(intent, requestCode)
//        activity.startActivityFromFragment(ScheduleFragment(), intent,1)
    }

}