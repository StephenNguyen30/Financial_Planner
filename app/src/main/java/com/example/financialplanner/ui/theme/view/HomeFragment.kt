package com.example.financialplanner.ui.theme.view


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
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
import com.example.financialplanner.ui.theme.base.formatAmount
import com.example.financialplanner.ui.theme.base.lengthOfLong
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentVM.currentPage = Int.MAX_VALUE/2
                resetViewPager()
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initListener()
        initUi()
        initObserver()
        initViewPager()
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        //setting UI to home screen!!!!!
        (activity as HomeActivity).let {
            it.showTopAppBar(true, getString(R.string.transactions))
            it.showBottomNavigation(true)
        }

        binding.tvMonth.text = parentVM.monthYear
        Log.d("Viewpager", "initUi: ${parentVM.monthYear}")
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
            binding.vpContent.setCurrentItem(parentVM.currentPage - 1, true)
            Log.d("Firebase", "currentTime: ${sdf1.format(cal.time)}")
        }
        binding.ivRightArrow.setOnClickListener {
            binding.vpContent.setCurrentItem(parentVM.currentPage + 1, true)
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
                    Log.d("Viewpager1", "$position ${parentVM.currentPage}")
                } else if (position < parentVM.currentPage) {
                    cal.add(Calendar.MONTH, -1)
                    Log.d("Viewpager2", "$position ${parentVM.currentPage}")
                }
                updateDateAndFetchTransactions()
                parentVM.currentPage = position
                Log.d("Viewpager", "$position ${parentVM.currentPage}")
            }
        })
    }

    private fun updateDateAndFetchTransactions() {
        binding.tvMonth.text = sdf.format(cal.time)
        parentVM.monthYear = sdf1.format(cal.time)
        parentVM.getTransactionsByDate(parentVM.userId, parentVM.monthYear)
    }

    private fun initViewPager() {
        val adapter = PageAdapter(this)
        binding.vpContent.adapter = adapter
        binding.vpContent.currentItem = parentVM.currentPage
        binding.vpContent.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
    }

    private fun initObserver() {
        parentVM.listTransactionByDate.observe(viewLifecycleOwner) { transactionMap ->
            Log.d("Firebase", "initObserver: $transactionMap")
            parentVM.income = 0
            parentVM.expenses = 0
            parentVM.total = 0
            transactionMap[parentVM.monthYear]?.forEach {
                if (it.type == "expenses") {
                    parentVM.expenses += it.amount.replace("[đ .]".toRegex(), "").toLong()
                } else if (it.type == "income") {
                    parentVM.income += it.amount.replace("[đ .]".toRegex(), "").toLong()
                }
            }
            parentVM.total = parentVM.income - parentVM.expenses
            binding.tvIncomeValue.text = parentVM.income.toString().formatAmount()
            binding.tvExpensesValue.text = parentVM.expenses.toString().formatAmount()
            binding.tvTotalValue.text = parentVM.total.toString().formatAmount()
        }
    }

    fun resetViewPager() {
        binding.vpContent.setCurrentItem(parentVM.currentPage, true)
    }

}
