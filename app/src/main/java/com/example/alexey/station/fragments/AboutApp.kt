package com.example.alexey.station.fragments

import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.station.BuildConfig

import com.example.alexey.station.R

class AboutApp : Fragment() {




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_about_app, container, false)
        val tv_version = view.findViewById<TextView>(R.id.tv_version)
        tv_version.text = tv_version.text.toString() +  BuildConfig.VERSION_NAME
        Log.d("FRAG", "fssdfgjhkl;jhgfsdxcgvb")

        return view
    }



}
