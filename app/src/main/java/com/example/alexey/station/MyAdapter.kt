package com.example.alexey.station

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.station.model.Cities
import com.example.alexey.station.model.Station

/**
 * Created by alexey on 14.02.18.
 */
class MyAdapter(private val listener: (Station) -> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    private var listStations: List<Station> = emptyList()

    fun setListStations(list: List<Station>) {
        this.listStations = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStations[position])
    }

    override fun getItemCount(): Int = listStations.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view, listener)
    }


    class ViewHolder(itemView: View, private val listener: (Station) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name_station)
        val tvCountry: TextView = itemView.findViewById(R.id.tv_country_station)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city_title_station)

        fun bind(station: Station) {
            tvName.text = station.stationTitle
            tvCountry.text = station.countryTitle
            tvCity.text = station.cityTitle
            itemView.setOnClickListener( { listener(station) })
        }

    }
}
