package com.example.financialplanner.ui.theme.adapter

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingAdapter
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.viewholder.TransactionInfoViewHolder


class TransactionInfoAdapter(
    private val categoryOnClick: (CategoryModel) -> Unit = {}
) : BaseBindingAdapter<CategoryModel>(CategoryModel.DIFF_CALLBACK) {
    override fun contentViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<CategoryModel, out ViewBinding> {
        return TransactionInfoViewHolder(parent, categoryOnClick)
    }
}

