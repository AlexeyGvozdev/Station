package com.example.alexey.station

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.alexey.station.fragments.AboutApp
import com.example.alexey.station.fragments.ScheduleFragment
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import android.util.Log
import java.io.IOException
import java.io.InputStream
import com.example.alexey.station.ListStationActivity.Companion.NUMBER_ELEMENT_MENU_NAV
import com.example.alexey.station.fragments.ScheduleFragment.Companion.TAG_SCHEDULE_FRAGMENT
import java.lang.Thread.sleep


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val TAG = "MYTAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
//        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        val resultFromListStationActivity = intent.getIntExtra(NUMBER_ELEMENT_MENU_NAV, 0)
        nav_view.setNavigationItemSelectedListener(this)
        if(savedInstanceState == null) {
            nav_view.menu.getItem(resultFromListStationActivity).isChecked = true
            onNavigationItemSelected(nav_view.menu.getItem(resultFromListStationActivity))
        }
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
        menuInflater.inflate(R.menu.menu, menu)
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


        val fTran = fragmentManager.beginTransaction()
        val fragment : Fragment
        var tagFragment: String = ""
        when (item.itemId) {
            R.id.schedule -> {
                fragment = ScheduleFragment()
                tagFragment = TAG_SCHEDULE_FRAGMENT


            }
            else -> {
                fragment = AboutApp()
                tagFragment = "AboutApp"
            }
        }
        fTran.replace(R.id.my_container, fragment, tagFragment).commit()
        title = item.toString()
//        Log.d(TAG, item.toString() + " : " + title)


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
