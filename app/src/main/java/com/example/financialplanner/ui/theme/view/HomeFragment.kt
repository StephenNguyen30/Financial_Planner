package com.example.financialplanner.ui.theme.view


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.financialplanner.R
import com.example.financialplanner.databinding.HomeFragmentBinding
import com.example.financialplanner.databinding.ItemCategoryBinding
import com.example.financialplanner.ui.theme.HomeActivity
import com.example.financialplanner.ui.theme.adapter.PageAdapter
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    override val viewModel: BaseViewModel by viewModels()
    private val parentVM: HomeViewModel by activityViewModels()

    private val timeZone = TimeZone.getDefault()
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val sdf1 = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(timeZone, Locale.ENGLISH)

    private val adapter: PageAdapter by lazy {
        PageAdapter(this)
    }

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
        initViewPager()
    }

    private fun initListener() {
        binding.ivLeftArrow.setOnClickListener {
            binding.vpContent.setCurrentItem(parentVM.currentPage - 1, false)
            Log.d("Firebase", "currentTime: ${sdf1.format(cal.time)}")
        }
        binding.ivRightArrow.setOnClickListener {
            binding.vpContent.setCurrentItem(parentVM.currentPage + 1, false)
            Log.d("Firebase", "currentTime: ${sdf1.format(cal.time)}")
        }
        binding.ivAddTransactions.setOnClickListener {
            this.getFragmentNavController(R.id.fragmentContainer)
                ?.navigate(R.id.action_homeFragment_to_transactionFragment)
        }
        binding.vpContent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position > parentVM.currentPage) {
                    cal.add(Calendar.MONTH, 1)
                } else if (position < parentVM.currentPage) {
                    cal.add(Calendar.MONTH, -1)
                }
                updateDateAndFetchTransactions()
                parentVM.currentPage = position
            }
        })
    }

    private fun updateDateAndFetchTransactions() {
        binding.tvMonth.text = sdf.format(cal.time)
        val monthYear = sdf1.format(cal.time)
        parentVM.getTransactionsByDate(parentVM.userId, monthYear)
    }

    private fun initViewPager() {
        binding.vpContent.adapter = adapter
        binding.vpContent.setCurrentItem(Int.MAX_VALUE / 2, false)
        binding.vpContent.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
    }

}
