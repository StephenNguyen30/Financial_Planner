package com.example.financialplanner.ui.theme.viewholder

import android.view.ViewGroup
import com.example.financialplanner.databinding.ItemTransactionInfoBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import com.example.financialplanner.ui.theme.model.CategoryModel

class CategoryViewHolder(
    parent: ViewGroup,
    private val categoryOnClick: (CategoryModel) -> Unit = {}
): BaseBindingViewHolder<CategoryModel, ItemTransactionInfoBinding>(parent get ItemTransactionInfoBinding::inflate) {
    override fun bind(data: CategoryModel) {
        binding.tvCategories.text = data.name
        binding.ivIcon.setImageResource(data.icon)
        binding.root.setOnClickListener{
            categoryOnClick.invoke(data)
        }
    }
}