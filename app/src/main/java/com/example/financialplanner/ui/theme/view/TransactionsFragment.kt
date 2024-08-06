package com.example.financialplanner.ui.theme.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialplanner.R
import com.example.financialplanner.databinding.TransactionsFragmentBinding
import com.example.financialplanner.ui.theme.HomeActivity
import com.example.financialplanner.ui.theme.adapter.CalendarAdapter
import com.example.financialplanner.ui.theme.adapter.CategoryAdapter
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.GridItemDecoration
import com.example.financialplanner.ui.theme.base.hideKeyboard
import com.example.financialplanner.ui.theme.base.setTextEditView
import com.example.financialplanner.ui.theme.base.titleCase
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.TransactionModel
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
    private var formatters: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

    private var addTransaction = TransactionModel()

    private val transactionAdapter: CategoryAdapter by lazy {
        CategoryAdapter(
            categoryOnClick = {
                if (binding.etCategory.isFocused && it.name != "Add Categories") {
                    viewModel.selectedCategory = it
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
                if (binding.etAccount.isFocused && it.name != "Add Accounts") {
                    binding.etAccount.setTextEditView(" ${it.name}")
                    bindingPopUp(false)
                }
                if (it.name == "Add Categories" || it.name == "Add Accounts") {
                    this.getFragmentNavController(R.id.fragmentContainer)
                        ?.navigate(R.id.action_transactionFragment_to_addCategoryFragment)
                }
            },
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
            viewModel.originalList = it
            viewModel.getTransCategory()
            if (binding.etCategory.isFocused && binding.rbExpenses.isChecked)
                transactionAdapter.submitList(it)
        }

        viewModel.incomeCategories.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe
            viewModel.getIncomeCategory()
            if (binding.etCategory.isFocused && binding.rbIncome.isChecked)
                transactionAdapter.submitList(it)
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
                    binding.tvSave.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_btn_radius_red_12
                        )
                    )
                    transactionAdapter.submitList(viewModel.incomeCategories.value)
                    clearData()
                }

                R.id.rbExpenses -> {
                    binding.appBar.tvTitle.text = getString(R.string.expenses)
                    binding.tvSave.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_btn_radius_green_12
                        )
                    )
                    transactionAdapter.submitList(viewModel.originalList)
                    clearData()
                }

                R.id.rbTransfer -> {
                    binding.appBar.tvTitle.text = getString(R.string.transfer)
                    binding.tvSave.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_btn_radius_black_12
                        )
                    )
                    clearData()
                }

            }
        }
        binding.ivLeftArrow.setOnClickListener {
            viewModel.moveToPreviousMonth()
        }
        binding.ivRightArrow.setOnClickListener {
            viewModel.moveToNextMonth()
        }
        val listEditText = listOf(
            binding.etDate,
            binding.etCategory,
            binding.etAccount,
            binding.etAmount,
            binding.etNote
        )
        binding.nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY != oldScrollY || !binding.nsv.canScrollVertically(1) || !binding.nsv.canScrollVertically(
                    0
                )
            ) {
                bindingPopUp(false)
                context?.hideKeyboard(this.requireView())
                listEditText.forEach {
                    it.clearFocus()
                }
            }
        })
        binding.tvClear.setOnClickListener {
            clearData()
        }

        binding.tvSave.setOnClickListener {
            val listOfString = listOf(
                binding.etDate.text.toString().trim(),
                binding.etCategory.text.toString().trim(),
                binding.etAmount.text.toString().trim(),
                binding.etAccount.text.toString().trim(),
                binding.etNote.text.toString().trim()
            )
            val isAnyEmpty = listOfString.any { it.isEmpty() }
            if (isAnyEmpty) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.rbExpenses.isChecked) {
                    val icon = viewModel.selectedCategory?.icon ?: 0
                    addTransaction = TransactionModel(
                        type = "expenses",
                        dayDate = binding.etDate.text.toString(),
                        categories = CategoryModel(binding.etCategory.text.toString(), icon, true),
                        amount = binding.etAmount.text.toString(),
                        accounts = CategoryModel(binding.etAccount.text.toString(), 0, false),
                        note = binding.etNote.text.toString(),
                    )
                } else if (binding.rbIncome.isChecked) {
                    val icon = viewModel.selectedCategory?.icon ?: 0
                    addTransaction = TransactionModel(
                        type = "income",
                        dayDate = binding.etDate.text.toString(),
                        categories = CategoryModel(binding.etCategory.text.toString(), icon, true),
                        amount = binding.etAmount.text.toString(),
                        accounts = CategoryModel(binding.etAccount.text.toString(), 0, false),
                        note = binding.etNote.text.toString(),
                    )
                }
                viewModel.addTransaction(viewModel.userId, addTransaction)
                findNavController().popBackStack()
            }
        }
    }

    fun initCategoryRcv() {
        binding.rcvCategory.layoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        binding.rcvCategory.adapter = transactionAdapter
        clearItemDecorations(binding.rcvCategory)
        binding.rcvCategory.addItemDecoration(
            GridItemDecoration(
                3,
                ContextCompat.getColor(requireContext(), R.color.grey_B3B1B1)
            )
        )
        binding.llCalendarMonth.isVisible = false
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initCalendarRcv() {
        binding.rcvCategory.layoutManager =
            GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false)
        binding.rcvCategory.adapter = calendarAdapter
        clearItemDecorations(binding.rcvCategory)
        binding.rcvCategory.addItemDecoration(
            GridItemDecoration(
                3,
                ContextCompat.getColor(requireContext(), R.color.grey_B3B1B1)
            )
        )
        binding.rcvCategory.setOnTouchListener { _, _ -> true }
        binding.llCalendarMonth.isVisible = true
    }

    fun bindingPopUp(boolean: Boolean) {
        binding.llPopUp.isVisible = boolean
        binding.llSelection.isVisible = !boolean
    }

    fun setTitlePopUpName(string: String) {
        binding.tvPopUpTitle.text = string
    }

    private fun clearItemDecorations(recyclerView: RecyclerView) {
        while (recyclerView.itemDecorationCount > 0) {
            recyclerView.removeItemDecorationAt(0)
        }
    }

    private fun clearData() {
        binding.etCategory.setTextEditView("")
        binding.etAccount.setTextEditView("")
        binding.etAmount.setTextEditView("")
        binding.etNote.setTextEditView("")
        binding.etCategory.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        bindingPopUp(false)
        context?.hideKeyboard(this.requireView())
        binding.etDate.setText(sdf.format(cal.time))
    }
}