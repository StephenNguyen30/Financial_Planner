package com.example.financialplanner.ui.theme.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingAdapter
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.model.TransactionModel
import com.example.financialplanner.ui.theme.viewholder.HomeContentDailyViewHolder

class HomeContentAdapter(
    private val positionOnClick: (Int) -> Unit = {}
) : BaseBindingAdapter<TransactionModel>(TransactionModel.DIFF_CALLBACK) {
    override fun contentViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<TransactionModel, out ViewBinding> {
        return HomeContentDailyViewHolder(parent, positionOnClick)
    }
}