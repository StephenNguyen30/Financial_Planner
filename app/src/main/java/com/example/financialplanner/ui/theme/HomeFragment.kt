package com.example.financialplanner.ui.theme


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.financialplanner.R
import com.example.financialplanner.databinding.HomeFragmentBinding
import com.example.financialplanner.databinding.ItemCategoryBinding
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()

    private val timeZone = TimeZone.getDefault()
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(timeZone, Locale.ENGLISH)


    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initListener()
        initUi()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        (activity as HomeActivity).let {
            it.showTopAppBar(true, getString(R.string.transactions))
            it.showBottomNavigation(true)
        }

        binding.tvMonth.text = sdf.format(cal.time)
        val list = listOf("Daily", "Monthly", "Annually")
        val tabLayout = binding.tlFilter
        for (i in list.indices) {
            val itemCategoryBinding = ItemCategoryBinding.inflate(layoutInflater)
            itemCategoryBinding.tvCategory.text = list[i]
            tabLayout.addTab(tabLayout.newTab().setCustomView(itemCategoryBinding.root))
            itemCategoryBinding.tvCategory.setTextColor(Color.BLACK)
        }
        binding.tvIncome.text = "Income"
        binding.tvExpenses.text = "Expenses"
        binding.tvTotal.text = "Total"


    }

    private fun initListener() {
        binding.ivLeftArrow.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            binding.tvMonth.text = sdf.format(cal.time)
        }
        binding.ivRightArrow.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            binding.tvMonth.text = sdf.format(cal.time)
        }
        binding.ivAddTransactions.setOnClickListener {
            this.getFragmentNavController(R.id.fragmentContainer)?.navigate(R.id.action_homeFragment_to_transactionFragment)
        }
    }

}
