package com.example.fitnessapp.Activities

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fitnessapp.Database.Photo
import com.example.fitnessapp.Fragments.*
import com.example.fitnessapp.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var actionBarToggle : ActionBarDrawerToggle
    private lateinit var sharedPrefs : SharedPreferences
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var mAdView : AdManagerAdView
    private val activity = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        val customBackground = getSharedPreferences("Background", Context.MODE_PRIVATE)

        InterstitialAd.load(this,"ca-app-pub-7793029180445743/2268087410", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(activity)
                }
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }

        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        sharedPrefs = getSharedPreferences("autoLogin", Context.MODE_PRIVATE)
        val color = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        window.statusBarColor = color.getInt("color", Color.BLUE)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        actionBarToggle = ActionBarDrawerToggle(this,drawerLayout, R.string.nav_open, R.string.nav_close)

        val navigationView : NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(color.getInt("color", Color.BLUE)))

        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val image = customBackground.getString("customBackground", null)
        if(image != null){
            drawerLayout.background = BitmapDrawable(resources, loadPhoto(image)?.bmp)
        }


        if(savedInstanceState == null && sharedPrefs.getInt("key", 0) == 0){
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, WelcomeFragment()).commit()
            navigationView.setCheckedItem(R.id.welcomePage)
        }else if(savedInstanceState == null && sharedPrefs.getInt("key", 0) == 1){
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, YourWorkoutsFragment()).commit()
            navigationView.setCheckedItem(R.id.customWorkouts)
        }
        else if(savedInstanceState == null && sharedPrefs.getInt("key", 0) == 2) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecommendedWorkoutsFragment()).commit()
            navigationView.setCheckedItem(R.id.recommendedWorkouts)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.welcomePage -> {
                sharedPrefs.edit().putInt("key", 0).apply()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, WelcomeFragment()).commit()
            }
            R.id.customWorkouts -> {
                sharedPrefs.edit().putInt("key", 1).apply()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, YourWorkoutsFragment()).commit()
            }
            R.id.recommendedWorkouts -> {
                sharedPrefs.edit().putInt("key", 2).apply()
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, RecommendedWorkoutsFragment()).commit()
            }
            R.id.prCalculator -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, PRCalculatorFragment()).commit()
            }
            R.id.settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SettingsFragment()).commit()
            }
        }
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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