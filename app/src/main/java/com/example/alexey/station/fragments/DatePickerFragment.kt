package com.example.alexey.station.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.DatePicker

import java.util.*


open class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnDatePickerListener {
        fun getDate(strDate: String)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        //Вызываем метод getDate() в SchedulerFragment
        (targetFragment as? OnDatePickerListener)?.getDate("$p3.${p2+1}.$p1")
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity, this, year, month, day)
    }

}

