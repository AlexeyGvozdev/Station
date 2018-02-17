package com.example.alexey.station

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.example.alexey.station.fragments.ScheduleFragment
import com.example.alexey.station.fragments.ScheduleFragment.Companion.SELECTED_DATE
import com.example.alexey.station.fragments.ScheduleFragment.Companion.TAG_SCHEDULE_FRAGMENT

import java.util.*


open class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnDatePickerListener {
        fun getDate(strDate: String)
    }
    val TAG = "FRAGM"
    private var mCallback: OnDatePickerListener? = null




    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        mCallback = targetFragment as? OnDatePickerListener
        mCallback?.getDate("$p3:$p2:$p1")
        Log.d("TAG", targetFragment?.tag?.toString() +  "sgfsdf ")
    }


    override fun onResume() {
        setTargetFragment(fragmentManager.findFragmentByTag(TAG_SCHEDULE_FRAGMENT), 3)
        Log.i(TAG, "onResume" + targetFragment?.toString())
        super.onResume()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity, this, year, month, day)
    }

}

