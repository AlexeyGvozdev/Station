package com.example.alexey.station

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.alexey.station.model.Station


class MyAdapter(private val listener: (Station) -> Unit,
                private val longListener: (Station) -> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    private lateinit var onclicklistener : View.OnClickListener
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
        return ViewHolder(view, listener, longListener)
    }


    class ViewHolder(itemView: View,
                     private val listener: (Station) -> Unit,
                     private val longListener: (Station) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name_station)
        val tvCountry: TextView = itemView.findViewById(R.id.tv_country_station)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city_title_station)

        fun bind(station: Station) {
            tvName.text = station.stationTitle
            tvCountry.text = station.countryTitle
            tvCity.text = station.cityTitle
            itemView.setOnClickListener( { listener(station) } )
            itemView.setOnLongClickListener {
                longListener(station)
                true
            }
        }

    }
}
