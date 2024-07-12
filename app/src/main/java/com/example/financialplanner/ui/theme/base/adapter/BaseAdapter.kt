package com.example.financialplanner.ui.theme.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T>(differCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseViewHolder<T>>(differCallback) {

    protected abstract fun contentViewHolder(): BaseViewHolder<T>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        contentViewHolder()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>, position: Int, payloads: MutableList<Any>
    ) {
        if (updateViewHolder(holder, position, payloads)) return
        super.onBindViewHolder(holder, position, payloads)
    }

    protected open fun updateViewHolder(
        holder: BaseViewHolder<T>, position: Int, payloads: MutableList<Any>
    ) = false

}

abstract class BaseBindingAdapter<T>(differCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseBindingViewHolder<T, out ViewBinding>>(differCallback) {

    protected abstract fun contentViewHolder(parent: ViewGroup, viewType: Int):
            BaseBindingViewHolder<T, out ViewBinding>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        contentViewHolder(parent, viewType)

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<T, out ViewBinding>, position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<T, out ViewBinding>, position: Int, payloads: MutableList<Any>
    ) {
        if (updateViewHolder(holder, position, payloads)) return
        super.onBindViewHolder(holder, position, payloads)
    }

    protected open fun updateViewHolder(
        holder: BaseBindingViewHolder<T, out ViewBinding>, position: Int, payloads: MutableList<Any>
    ) = false

}