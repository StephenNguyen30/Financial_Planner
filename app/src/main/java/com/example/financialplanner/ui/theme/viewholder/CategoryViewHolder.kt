package com.example.financialplanner.ui.theme.viewholder

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.example.financialplanner.R
import com.example.financialplanner.databinding.ItemTransactionInfoBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import com.example.financialplanner.ui.theme.model.CategoryModel

class CategoryViewHolder(
    parent: ViewGroup,
    private val categoryOnClick: (CategoryModel) -> Unit = {},
) : BaseBindingViewHolder<CategoryModel, ItemTransactionInfoBinding>(parent get ItemTransactionInfoBinding::inflate) {
    override fun bind(data: CategoryModel) {
        binding.tvCategories.text = data.name
        binding.root.setOnClickListener {
            categoryOnClick.invoke(data)
        }
        val view = binding.tvCategories
        if (data.icon == 0) {
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = itemView.context.resources.getDimensionPixelSize(R.dimen.d_0_0dp)
            }
        } else {
            binding.ivIcon.setImageResource(data.icon)
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = itemView.context.resources.getDimensionPixelSize(R.dimen.d_22dp)
            }
        }


    }
}