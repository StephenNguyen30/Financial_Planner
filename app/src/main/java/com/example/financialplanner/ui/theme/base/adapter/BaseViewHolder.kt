package com.example.financialplanner.ui.theme.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(private val root: View) : RecyclerView.ViewHolder(root) {
    abstract fun bind(data: T)
}

abstract class BaseBindingViewHolder<T, B : ViewBinding>(
    protected val binding: B
) : BaseViewHolder<T>(binding.root)
