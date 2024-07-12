package com.example.financialplanner.ui.theme.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingAdapter
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.model.CalendarModel
import com.example.financialplanner.ui.theme.viewholder.CalendarViewHolder

class CalendarAdapter(
    private val dateOnClick: (CalendarModel) -> Unit = {}
) : BaseBindingAdapter<CalendarModel>(CalendarModel.DIFF_CALLBACK) {

    override fun contentViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<CalendarModel, out ViewBinding> {
        return CalendarViewHolder(parent, dateOnClick)
    }
}