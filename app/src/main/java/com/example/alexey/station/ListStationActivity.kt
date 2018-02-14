package com.example.alexey.station

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.alexey.station.model.DataAboutStations
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_station.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import java.io.IOException
import java.io.InputStream
import java.util.*

class ListStationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG = "MYTAG"
    var requestCodeFromFragment: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_station)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        var strJSON: String = ""
        strJSON = getStringFromAssetFile()


        val index = strJSON.indexOf("\"citiesTo\"")
        Log.d(TAG, strJSON.subSequence(index, index+10).toString())

        val gson = Gson()
//        val data: DataAboutStations = gson.fromJson(strJSON,DataAboutStations::class.java)


        val intentFromFragment = intent
        requestCodeFromFragment = intentFromFragment.getIntExtra(REQUEST_CODE_STRING,0)
        val intent = Intent()
        when (requestCodeFromFragment) {
            REQUEST_CODE_STATION_FROM -> {
                intent.putExtra(SELECTED_STATION, "this station")
                setResult(Activity.RESULT_OK, intent)
            }
            REQUEST_CODE_STATION_IN -> {
                intent.putExtra(SELECTED_STATION, "this station")
                setResult(Activity.RESULT_OK, intent)
            }
        }
        nav_view.setNavigationItemSelectedListener(this)
        finish()
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
        var intent: Intent = when (item.itemId) {
            R.id.schedule -> {
                Intent(this, MenuActivity::class.java)
            }
            else -> {
                Intent(this, MenuActivity::class.java)
            }
        }
        var resultCode: Int = when (item.itemId) {
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
        val REQUEST_CODE_STRING = "RequestCode"
        val REQUEST_CODE_STATION_IN = 2
        val REQUEST_CODE_STATION_FROM = 1
        val NUMBER_ELEMENT_MENU_NAV = "NumberElementMenuNav"
        val SELECTED_STATION = "SelectedStation"
    }
}

