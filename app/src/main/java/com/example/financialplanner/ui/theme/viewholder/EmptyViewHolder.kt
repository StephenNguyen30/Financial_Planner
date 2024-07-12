package com.example.financialplanner.ui.theme.viewholder

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.example.financialplanner.databinding.EmptyLayoutBinding
import com.example.financialplanner.databinding.ItemCircularProgressBarBinding
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get

class EmptyViewHolder(
    val parent: ViewGroup
) : BaseBindingViewHolder<HomeUiModel, EmptyLayoutBinding>(parent get EmptyLayoutBinding::inflate) {
    override fun bind(data: HomeUiModel) {
    }
}