package com.example.financialplanner.ui.theme.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.financialplanner.ui.theme.view.HomeContentFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val homeContentFragment  = HomeContentFragment()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, position - (Int.MAX_VALUE /2))
        val monthYear = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(calendar.time)

        homeContentFragment.arguments = Bundle().apply {
            putString("monthYear", monthYear)
        }
        return homeContentFragment
    }
}