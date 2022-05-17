package com.example.fitnessapp.Activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.fitnessapp.Adapters.ViewPagerAdapter
import com.example.fitnessapp.Database.Photo
import com.example.fitnessapp.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class WorkoutActivity : AppCompatActivity() {

    private lateinit var mAdView : AdManagerAdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val customBackground = getSharedPreferences("Background", Context.MODE_PRIVATE)
        val layout : ConstraintLayout = findViewById(R.id.workoutLayout)
        val image = customBackground.getString("customBackground", null)
        if(image != null){
            layout.background = BitmapDrawable(resources, loadPhoto(image)?.bmp)
        }

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val adapter = ViewPagerAdapter(this)
        val tabSelector : TabLayout = findViewById(R.id.daysTabLayout)
        val viewPager : ViewPager2 = findViewById(R.id.workoutViewPager2)
        val workoutTitle : TextView = findViewById(R.id.workoutNameTextView)
        val actionBar = supportActionBar
        val sharedPrefs = getSharedPreferences("chosenWorkout", Context.MODE_PRIVATE)
        val workoutName = sharedPrefs.getString("workoutName", null)
        val sharedPreferences = getSharedPreferences("days", Context.MODE_PRIVATE)
        val color = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        window.statusBarColor = color.getInt("color", Color.BLUE)
        tabSelector.setSelectedTabIndicatorColor(color.getInt("color", Color.BLUE))

        actionBar?.setBackgroundDrawable(ColorDrawable(color.getInt("color", Color.BLUE)))

        val nightModeFlags = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> tabSelector.setTabTextColors(Color.WHITE, color.getInt("color", Color.BLUE))
            Configuration.UI_MODE_NIGHT_NO -> tabSelector.setTabTextColors(Color.BLACK, color.getInt("color", Color.BLUE))
            Configuration.UI_MODE_NIGHT_UNDEFINED -> tabSelector.setTabTextColors(Color.WHITE, color.getInt("color", Color.BLUE))
        }

        actionBar?.setDisplayHomeAsUpEnabled(true)
        workoutTitle.text = workoutName
        workoutTitle.setTextColor(color.getInt("color", Color.BLUE))

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 7

        TabLayoutMediator(tabSelector, viewPager, fun(tab : TabLayout.Tab, position){
            when(position) {
                0 -> tab.text = "Monday"
                1 -> tab.text = "Tuesday"
                2 -> tab.text = "Wednesday"
                3 -> tab.text = "Thursday"
                4 -> tab.text = "Friday"
                5 -> tab.text = "Saturday"
                6 -> tab.text = "Sunday"
            }
        }).attach()

        when(sharedPreferences.getString("day", workoutName + "Monday")){
            workoutName + "Tuesday" -> {
                tabSelector.getTabAt(1)!!.select()
                viewPager.currentItem = 1
            }
            workoutName + "Wednesday" -> {
                tabSelector.getTabAt(2)!!.select()
                viewPager.currentItem = 2
            }
            workoutName + "Thursday" -> {
                tabSelector.getTabAt(3)!!.select()
                viewPager.currentItem = 3
            }
            workoutName + "Friday" -> {
                tabSelector.getTabAt(4)!!.select()
                viewPager.currentItem = 4
            }
            workoutName + "Saturday" -> {
                tabSelector.getTabAt(5)!!.select()
                viewPager.currentItem = 5
            }
            workoutName + "Sunday" -> {
                tabSelector.getTabAt(6)!!.select()
                viewPager.currentItem = 6
            }
            else -> {
                tabSelector.getTabAt(0)!!.select()
                viewPager.currentItem = 0
            }
        }

        tabSelector.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                sharedPreferences.edit().putString("day", workoutName + tab.text.toString()).apply()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
    }
    private fun loadPhoto(filename: String) : Photo? {
        val files = filesDir.listFiles()
        files?.filter { it.canRead() && it.isFile && it.name.startsWith(filename) && it.name.endsWith(".jpg") }?.map {
            val bytes = it.readBytes()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            return Photo(it.name, bmp)
        }
        return null
    }
}