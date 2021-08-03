package com.example.tour_guide_nepal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tour_guide_nepal.adapter.ViewPagerAdapter
import com.example.tour_guide_nepal.fragments.AboutUsFragment
import com.example.tour_guide_nepal.fragments.ProfileFragment
import com.example.tour_guide_nepal.fragments.Select_cityFragment
import com.example.tour_guide_nepal.fragments.Selectplaces
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var lstTitle: ArrayList<String>
    private lateinit var lstFragments: ArrayList<Fragment>
    private lateinit var viewpager2: ViewPager2
    private lateinit var tablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager2 = findViewById(R.id.viewpager)
        tablayout = findViewById(R.id.tablayout)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer)
        val navView: NavigationView = findViewById(R.id.navmenu)

        // check for permission
        if (!hasPermission()){
            requestPermission()
        }

        populateList()
        val adapter = ViewPagerAdapter(lstFragments, supportFragmentManager, lifecycle)
        viewpager2.adapter = adapter
        TabLayoutMediator(tablayout, viewpager2) { tab, position ->
            tab.text = lstTitle[position]
        }.attach()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                R.id.nav_select_city -> {
                    startActivity(Intent(this, Selectplaces::class.java))
                }
                R.id.nav_bookhotel -> {
                    startActivity(Intent(this, Hotelbooking_Activity::class.java))
                }
                R.id.nav_viewbookhotel -> {
                    startActivity(Intent(this, HotelBookingInfo::class.java))
                }
                R.id.nav_contact -> {
                    Toast.makeText(
                        applicationContext,
                        "Emergency Contact Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.nav_terms -> Toast.makeText(
                    applicationContext,
                    "Terms and Services Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_rateapp -> Toast.makeText(
                    applicationContext,
                    "Rate App Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> {
                    logout()
                }
            }

            true

        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        startActivity(Intent(this,LoginActivity::class.java))
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            permissions,1
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED)
            {
                hasPermission = false
            }
        }
        return hasPermission
    }

    private fun populateList() {
        lstTitle = ArrayList<String>()
        lstTitle.add("Home")
        lstTitle.add("Profile")
        lstTitle.add("About Us")
        lstFragments = ArrayList<Fragment>()
        lstFragments.add(Select_cityFragment())
        lstFragments.add(ProfileFragment())
        lstFragments.add(AboutUsFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}