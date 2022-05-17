package com.example.fitnessapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitnessapp.Fragments.Days.*

class ViewPagerAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when(position){
            1 -> return TuesdayFragment()
            2 -> return WednesdayFragment()
            3 -> return ThursdayFragment()
            4 -> return FridayFragment()
            5 -> return SaturdayFragment()
            6 -> return SundayFragment()
        }
        return MondayFragment()
    }

    override fun getItemCount(): Int {
        return 7
    }
}