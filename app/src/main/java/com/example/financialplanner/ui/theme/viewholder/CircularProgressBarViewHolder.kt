package com.example.financialplanner.ui.theme.viewholder

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.financialplanner.R
import com.example.financialplanner.databinding.ItemCircularProgressBarBinding
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get

class CircularProgressBarViewHolder (
    val parent: ViewGroup
) : BaseBindingViewHolder<HomeUiModel, ItemCircularProgressBarBinding>(parent get ItemCircularProgressBarBinding::inflate){
    @SuppressLint("SetTextI18n")
    override fun bind(data: HomeUiModel) {
        binding.tvTotalSpent.text = "Total Budget"
        binding.tvAmountSpent.text = "${data.totalBudget}"
        binding.cpbProgress.progress = 75
    }

}