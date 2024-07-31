package com.example.financialplanner.ui.theme.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.example.financialplanner.ui.theme.HomeUiModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.Calendar
import java.util.Date

@Parcelize
data class TransactionModel(
    val type : String,
    val dayDate: String,
    val categories: CategoryModel = CategoryModel(),
    val amount: String,
    val accounts: CategoryModel = CategoryModel(),
    val note: String = "",
) : Parcelable {
    constructor() : this ("", "", CategoryModel(), "", CategoryModel(), "")

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionModel>() {
            override fun areItemsTheSame(oldItem: TransactionModel, newItem: TransactionModel) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel,
            ) =
                oldItem == newItem
        }
    }
}

@Parcelize
data class CategoryModel(
    val name: String = "",
    val icon: Int = 0,
    val isCategory: Boolean = false
) : Parcelable{
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryModel>() {
            override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CategoryModel,
                newItem: CategoryModel,
            ) =
                oldItem == newItem
        }
    }
}
