package com.example.financialplanner.ui.theme

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialplanner.R
import com.example.financialplanner.databinding.TransactionsFragmentBinding
import com.example.financialplanner.ui.theme.adapter.CalendarAdapter
import com.example.financialplanner.ui.theme.adapter.CategoryAdapter
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.GridItemDecoration
import com.example.financialplanner.ui.theme.base.setTextEditView
import com.example.financialplanner.ui.theme.base.titleCase
import com.example.financialplanner.ui.theme.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@AndroidEntryPoint
class TransactionsFragment :
    BaseFragment<TransactionsFragmentBinding>(TransactionsFragmentBinding::inflate) {
    override val viewModel: TransactionViewModel by viewModels()

    private val timeZone = TimeZone.getDefault()
    private val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(timeZone, Locale.ENGLISH)
    private var formatters: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    private val transactionAdapter: CategoryAdapter by lazy {
        CategoryAdapter(
            categoryOnClick = {
                if (binding.etCategory.isFocused) {
                    val drawable = ContextCompat.getDrawable(requireContext(), it.icon)
                    binding.etCategory.setCompoundDrawablesWithIntrinsicBounds(
                        drawable,
                        null,
                        null,
                        null
                    )
                    binding.etCategory.setTextEditView(" ${it.name}")
                    bindingPopUp(false)
                }
                if (binding.etAccount.isFocused){
                    binding.etAccount.setTextEditView(" ${it.name}")
                    bindingPopUp(false)
                }
            }
        )
    }

    private val calendarAdapter: CalendarAdapter by lazy {
        CalendarAdapter(
            dateOnClick = {
                val day = it.date ?: return@CalendarAdapter
                binding.etDate.setTextEditView(day.format(formatters))
            }
        )
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initUI()
        initObserver()
        initListener()

    }

    private fun initUI() {
        (activity as HomeActivity).showTopAppBar(false, "")
        binding.etDate.setText(sdf.format(cal.time))
        binding.rbExpenses.isChecked = true
        binding.appBar.tvTitle.text = getString(R.string.expenses)
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.categories.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe
            viewModel.getTransCategory()
            if (binding.etCategory.isFocused) transactionAdapter.submitList(it)
        }
        viewModel.days.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe
            calendarAdapter.submitList(it)
        }
        viewModel.account.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe
            viewModel.getTransAccount()
            if (binding.etAccount.isFocused) transactionAdapter.submitList(it)
        }
        val currentMonth = YearMonth.now()
        viewModel.yearMonth.observe(viewLifecycleOwner) {
            viewModel.getTransCalendar(currentMonth)
            if (it == currentMonth) {
                binding.tvMonth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.orange
                    )
                )
            } else {
                binding.tvMonth.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
            binding.tvMonth.text = "${it.month.toString().titleCase()} ${it.year}"
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.appBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbIncome -> {
                    binding.appBar.tvTitle.text = getString(R.string.income)
                }

                R.id.rbExpenses -> {
                    binding.appBar.tvTitle.text = getString(R.string.expenses)
                }

                R.id.rbTransfer -> {
                    binding.appBar.tvTitle.text = getString(R.string.transfer)
                }

            }
        }
        binding.ivLeftArrow.setOnClickListener {
            viewModel.moveToPreviousMonth()
        }
        binding.ivRightArrow.setOnClickListener {
            viewModel.moveToNextMonth()
        }

    }

    fun initCategoryRcv() {
        binding.rcvCategory.layoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        binding.rcvCategory.adapter = transactionAdapter
        clearItemDecorations(binding.rcvCategory)
        binding.rcvCategory.addItemDecoration(GridItemDecoration(3, Color.GRAY))
        binding.llCalendarMonth.isVisible = false
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initCalendarRcv() {
        binding.rcvCategory.layoutManager =
            GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false)
        binding.rcvCategory.adapter = calendarAdapter
        clearItemDecorations(binding.rcvCategory)
        binding.rcvCategory.addItemDecoration(GridItemDecoration(3, Color.GRAY))
        binding.rcvCategory.setOnTouchListener { _, _ -> true }
        binding.llCalendarMonth.isVisible = true
    }

    fun bindingPopUp(boolean: Boolean) {
        binding.llPopUp.isVisible = boolean
    }

    fun setTitlePopUpName(string: String) {
        binding.tvPopUpTitle.text = string
    }

    private fun clearItemDecorations(recyclerView: RecyclerView) {
        while (recyclerView.itemDecorationCount > 0) {
            recyclerView.removeItemDecorationAt(0)
        }
    }
}