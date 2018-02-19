package com.example.alexey.station

import android.app.Activity
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.alexey.station.fragments.InformationDialogAboutStation
import com.example.alexey.station.model.Station
import kotlinx.android.synthetic.main.activity_list_station.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_list_station.*
import kotlin.collections.ArrayList

class ListStationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = "MYTAG"
    private var requestCodeFromFragment: Int = 0
    private var listStation: List<Station> = emptyList()

    private val adapter = MyAdapter(
            { returnSelectedStation(it) },
            { showInformationAboutStation(it) }
    )

    /*
    * Метод вызывается при долгом нажатии на элемент списка
    * Открывает диалог с данными о станции
    */
    private fun showInformationAboutStation(station: Station) {

        val dialog = InformationDialogAboutStation()
        val bundle = Bundle()
        bundle.putSerializable(KEY_STATION, station)
        dialog.arguments = bundle

        dialog.show(fragmentManager, "info")
    }
    /*
    * Метод вызывается при клике на элемент списка
    * Возвращяет в ScheduleFragment название выбранной станции
    * */
    private fun returnSelectedStation(station: Station) {
        val intent = Intent()
        intent.putExtra(SELECTED_STATION, station.stationTitle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_station)

        requestCodeFromFragment = intent.getIntExtra(REQUEST_CODE_STRING,0)
        initUI()
        readAssets()
    }

    private fun initUI() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {  }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  }
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Если ничего не введено, то выводим полный список станций

                if (charSequence.toString().isEmpty()) {
                    adapter.setListStations(listStation)
                } else {
                    val customListStation: ArrayList<Station> = ArrayList()
                    for (station in listStation) {
                        if (station.stationTitle.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            customListStation.add(station)
                        }
                    }
                    adapter.setListStations(customListStation)
                }
            }
        })

        nav_view.setNavigationItemSelectedListener(this)
    }

    /*
    * Метод запускает инициализацию лоадера
    * */
    private fun readAssets() {
        val callback: LoaderManager.LoaderCallbacks<List<Station>> = StationCallback()
        supportLoaderManager.initLoader(1, Bundle.EMPTY, callback)
    }

    inner class StationCallback() : LoaderManager.LoaderCallbacks<List<Station>> {
        override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<List<Station>> = StationReadLoader(this@ListStationActivity, requestCodeFromFragment)

        override fun onLoadFinished(loader: Loader<List<Station>>?, data: List<Station>?) { showStation(data) }

        override fun onLoaderReset(p0: Loader<List<Station>>?) {  }

    }

    private fun showStation(listStation: List<Station>?) {
        progress.visibility = View.GONE
        this.listStation = listStation ?: emptyList()
        adapter.setListStations(listStation ?: return)
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

    companion object {
        const val KEY_STATION = "Key station"
        const val REQUEST_CODE_STRING = "RequestCode"
        const val REQUEST_CODE_STATION_IN = 2
        const val REQUEST_CODE_STATION_FROM = 1
        const val NUMBER_ELEMENT_MENU_NAV = "NumberElementMenuNav"
        const val SELECTED_STATION = "SelectedStation"
    }
}

