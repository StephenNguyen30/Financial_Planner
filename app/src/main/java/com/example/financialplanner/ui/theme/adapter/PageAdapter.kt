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
        val monthYear = calculateMonthYear(position)
        homeContentFragment.arguments = Bundle().apply {
            putString("monthYear", monthYear)
        }
        return homeContentFragment
    }
    private fun calculateMonthYear(position: Int): String {
        val baseCalendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, position - Int.MAX_VALUE / 2)
        }
        val sdf = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
        return sdf.format(baseCalendar.time)
    }
}