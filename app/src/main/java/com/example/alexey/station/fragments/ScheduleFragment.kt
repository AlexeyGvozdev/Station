package com.example.alexey.station.fragments


import android.app.*
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.station.DatePickerFragment
import com.example.alexey.station.ListStationActivity
import com.example.alexey.station.ListStationActivity.Companion.SELECTED_STATION
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_FROM
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_IN
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STRING

import com.example.alexey.station.R
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment(), DatePickerFragment.OnDatePickerListener {


    override fun getDate(strDate: String) {
        setTextAndShow(tv_station_date, strDate)
    }


    val TAG = "FRAGM"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_schedule, container, false)


        val btn_choose_station_from = view.findViewById<View>(R.id.btn_choose_station_from)
        val btn_choose_station_in = view.findViewById<View>(R.id.btn_choose_station_in)
        val btn_choose_date = view.findViewById<View>(R.id.btn_choose_date)
        btn_choose_station_from.setOnClickListener( { openListStationActivity(REQUEST_CODE_STATION_FROM) } )
        btn_choose_station_in.setOnClickListener( { openListStationActivity(REQUEST_CODE_STATION_IN) } )
        btn_choose_date.setOnClickListener { showDatePickerDialog() }
        Log.d(TAG, "onCreateView")
        var text: String = savedInstanceState?.getCharSequence(KEY_STATION_IN)?.toString() ?: ""
//        if(text.isNotEmpty()) setTextAndShow(tv_station_in, text)

        Log.d(TAG, text + " : " + "" + text.length)
        retainInstance = true
        return view
    }

    private fun showDatePickerDialog() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(fragmentManager, "dialog")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == Activity.RESULT_OK) {

            val selectedStation = intent?.getStringExtra(SELECTED_STATION) ?: ""

            if (!selectedStation.isEmpty()) {
                when (requestCode) {
                    REQUEST_CODE_STATION_FROM -> {
                        setTextAndShow(tv_station_from, selectedStation)
                    }
                    REQUEST_CODE_STATION_IN -> {
                        setTextAndShow(tv_station_in, selectedStation)
                    }
                }
            }
        }
    }


    private fun setTextAndShow(textView: TextView, text: String) {
        textView.text = text
        textView.visibility = View.VISIBLE
    }


    private fun openListStationActivity(requestCode: Int) {
        val intent = Intent(activity, ListStationActivity::class.java)
        intent.putExtra(REQUEST_CODE_STRING, requestCode)
        startActivityForResult(intent, requestCode)
    }

    companion object {
        val SELECTED_DATE = "SelectedDate"
        val TAG_SCHEDULE_FRAGMENT = "ScheduleFragment"
    }

    val KEY_STATION_IN: String = "station in"
    val KEY_STATION_FROM: String = "station from"
    val KEY_DATE: String = "date"
    override fun onSaveInstanceState(outState: Bundle?) {
        if (tv_station_in.visibility == View.VISIBLE) outState?.putCharSequence(KEY_STATION_IN, tv_station_in.text)
        if (tv_station_from.visibility == View.VISIBLE) outState?.putCharSequence(KEY_STATION_FROM, tv_station_from.text)
        if (tv_station_date.visibility == View.VISIBLE) outState?.putCharSequence(KEY_DATE, tv_station_date.text)
        Log.d(TAG, "onSave")
        super.onSaveInstanceState(outState)
    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//
//    }
}
