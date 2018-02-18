package com.example.alexey.station

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.alexey.station.model.DataAboutStations
import com.example.alexey.station.model.Station
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_station.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_list_station.*
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.ArrayList

class ListStationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val adapter = MyAdapter(
            { returnSelectedStation(it) },
            { showInformationAboutStation(it) }
    )

    private fun showInformationAboutStation(station: Station) {
//        Toast.makeText(this, station.stationTitle, Toast.LENGTH_SHORT).show()

        val dialog = InformationDialogAboutStation()
        val bundle: Bundle = Bundle()
        bundle.putSerializable(KEY_STATION, station)
        dialog.arguments = bundle

        dialog.show(fragmentManager, "info")
    }

    private fun returnSelectedStation(station: Station) {
        val intent = Intent()
        intent.putExtra(SELECTED_STATION, station.stationTitle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    val TAG = "MYTAG"
    var requestCodeFromFragment: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_station)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()




        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()


        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val intentFromFragment = intent
        requestCodeFromFragment = intentFromFragment.getIntExtra(REQUEST_CODE_STRING,0)

        Observable.fromCallable { parseSTRJSON(getStringFromAssetFile()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { fillRecyclerView(it)},
                        { onEror(it)}
                )

    }

    private fun onEror(err: Throwable?) {
        Log.d(TAG, err.toString())
    }

    private fun fillRecyclerView(data: DataAboutStations?) {
        val listStations : ArrayList<Station> = ArrayList<Station>()
        if(data == null ) return

        when (requestCodeFromFragment) {
            REQUEST_CODE_STATION_FROM -> {
                data.citiesFrom.flatMapTo(listStations) { it.stations }
            }
            REQUEST_CODE_STATION_IN -> {
                data.citiesTo.flatMapTo(listStations) { it.stations }
            }
        }

        adapter.setListStations(listStations)
        progress.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.list_station, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val intent: Intent = when (item.itemId) {
            R.id.schedule -> {
                Intent(this, MenuActivity::class.java)
            }
            else -> {
                Intent(this, MenuActivity::class.java)
            }
        }
        val resultCode: Int = when (item.itemId) {
            R.id.schedule -> 0
            else -> 1
        }
        intent.putExtra(NUMBER_ELEMENT_MENU_NAV, resultCode)
        startActivity(intent)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getStringFromAssetFile(): String {
        val text = "allStations.json"
        var buffer: ByteArray? = null
        val inputStream: InputStream
        try {
            inputStream = assets.open(text)
            val size = inputStream.available()
            buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return String(buffer!!)
    }

    companion object {
        const val KEY_STATION = "Key station"
        const val REQUEST_CODE_STRING = "RequestCode"
        const val REQUEST_CODE_STATION_IN = 2
        const val REQUEST_CODE_STATION_FROM = 1
        const val NUMBER_ELEMENT_MENU_NAV = "NumberElementMenuNav"
        const val SELECTED_STATION = "SelectedStation"
    }


    private fun parseSTRJSON(strJSON: String): DataAboutStations {
        val index = strJSON.indexOf("\"citiesTo\"")
        Log.d(TAG, strJSON.subSequence(index, index+10).toString())
        val gson = Gson()
        return gson.fromJson(strJSON,DataAboutStations::class.java)
    }
}

