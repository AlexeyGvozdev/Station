package com.example.alexey.station.fragments


import android.app.*
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.station.ListStationActivity
import com.example.alexey.station.ListStationActivity.Companion.SELECTED_STATION
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_FROM
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STATION_IN
import com.example.alexey.station.ListStationActivity.Companion.REQUEST_CODE_STRING

import com.example.alexey.station.R
import kotlinx.android.synthetic.main.fragment_schedule.*


class ScheduleFragment : Fragment(), DatePickerFragment.OnDatePickerListener {

    val TAG = "FRAGM"

    /*
    * Вешаем обработчик нажатия на кнопки
    * */
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

        return view
    }

    private fun showDatePickerDialog() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.setTargetFragment(this, 3)
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

    private val KEY_STATION_IN: String = "station in"
    private val KEY_STATION_FROM: String = "station from"
    private val KEY_DATE: String = "date"

    /*
    * При повороте экрана сохраняем данные
    * */
    override fun onSaveInstanceState(outState: Bundle?) {
        if (tv_station_in.visibility == View.VISIBLE) outState?.putCharSequence(KEY_STATION_IN, tv_station_in.text)
        if (tv_station_from.visibility == View.VISIBLE) outState?.putCharSequence(KEY_STATION_FROM, tv_station_from.text)
        if (tv_station_date.visibility == View.VISIBLE) outState?.putCharSequence(KEY_DATE, tv_station_date.text)
        Log.d(TAG, "onSave")
        super.onSaveInstanceState(outState)
    }

    /*
    * Восстонавливаем данные
    * */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        var text: String = savedInstanceState?.getCharSequence(KEY_STATION_IN)?.toString() ?: ""
        if(text.isNotEmpty()) setTextAndShow(tv_station_in, text)
        text = savedInstanceState?.getCharSequence(KEY_STATION_FROM)?.toString() ?: ""
        if(text.isNotEmpty()) setTextAndShow(tv_station_from, text)
        text = savedInstanceState?.getCharSequence(KEY_DATE)?.toString() ?: ""
        if(text.isNotEmpty()) setTextAndShow(tv_station_date, text)

        Log.d(TAG, text + " : " + "" + text.length)

    }

    // Метод вызываемый из диалога выбора даты
    override fun getDate(strDate: String) {
        setTextAndShow(tv_station_date, strDate)
    }

    companion object {
        val TAG_SCHEDULE_FRAGMENT = "ScheduleFragment"
    }
}
