package com.example.financialplanner.ui.theme.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanModel(
    val type: String,
    val spent: Long = 0,
    val income: Long = 0,
    val isIncome: Boolean = false,

): Parcelable{

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlanModel>() {
            override fun areItemsTheSame(oldItem: PlanModel, newItem: PlanModel) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PlanModel,
                newItem: PlanModel,
            ) =
                oldItem == newItem
        }
    }


}
