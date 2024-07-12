package com.example.financialplanner.ui.theme.viewholder

import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.example.financialplanner.R
import com.example.financialplanner.databinding.ItemCalendarBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import com.example.financialplanner.ui.theme.model.CalendarModel

class CalendarViewHolder(
    parent: ViewGroup,
    private val dateOnClick: (CalendarModel) -> Unit = {}
) : BaseBindingViewHolder<CalendarModel, ItemCalendarBinding>(parent get ItemCalendarBinding::inflate) {
    override fun bind(data: CalendarModel) {
        val day = data.date
        if (data.dayName != null) {
            binding.tvDate.text = data.dayName
            binding.tvDate.setTextColor(getColor(itemView.context, R.color.black))
        } else {
            val textColor = when {
                data.isToday -> getColor(itemView.context, R.color.orange)
                data.isCurrentMonth -> getColor(itemView.context, R.color.black)
                else -> getColor(itemView.context, R.color.grey_B3B1B1)
            }
            binding.tvDate.setTextColor(textColor)
            binding.tvDate.text = day?.dayOfMonth.toString()
            binding.tvDate.setOnClickListener {
                dateOnClick.invoke(data)
            }
        }

    }
}