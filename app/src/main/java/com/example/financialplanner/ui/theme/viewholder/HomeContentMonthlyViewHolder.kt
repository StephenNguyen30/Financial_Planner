package com.example.financialplanner.ui.theme.viewholder

import android.view.ViewGroup
import com.example.financialplanner.databinding.ItemHomeContentMonthlyBinding
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeContentMonthlyViewHolder(
    parent: ViewGroup,
    private val monthOnClick: () -> Unit = {}
): BaseBindingViewHolder<HomeUiModel, ItemHomeContentMonthlyBinding>(parent get ItemHomeContentMonthlyBinding::inflate) {
    override fun bind(data: HomeUiModel) {
        val monthlyTransaction = data.monthlyTransactions
        binding.tvDate.text = monthlyTransaction.month
        binding.tvStartDate.text = getStartAndEndOfMonth(monthlyTransaction.month).first
        binding.tvEndDate.text = getStartAndEndOfMonth(monthlyTransaction.month).second
        binding.tvIncome.text = monthlyTransaction.income.toString()
        binding.tvExpenses.text = monthlyTransaction.expenses.toString()
        binding.root.setOnClickListener{
            monthOnClick.invoke()
        }
    }

    private fun getStartAndEndOfMonth(monthString: String): Pair<String, String> {
        val calendar = Calendar.getInstance()
        val month = getMonthNumber(monthString)
        calendar.set(month - 1, 1)
        val startDay = SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.time)
        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endDay = SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.time)

        return Pair(startDay, endDay)
    }
    private fun getMonthNumber(monthString: String): Int {
        val months = listOf("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
        return months.indexOf(monthString) + 1
    }
}