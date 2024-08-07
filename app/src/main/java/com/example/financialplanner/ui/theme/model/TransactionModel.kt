package com.example.financialplanner.ui.theme.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.example.financialplanner.ui.theme.enums.CategoryType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionModel(
    val type: String,
    val dayDate: String,
    val categories: CategoryModel = CategoryModel(),
    val amount: String,
    val accounts: CategoryModel = CategoryModel(),
    val note: String = "",
) : Parcelable {
    constructor() : this("", "", CategoryModel(), "", CategoryModel(), "")

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
    val isCategory: Boolean = true,
    val iconType: String = "",
) : Parcelable {
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

fun CategoryModel.toModel() = this.copy(
    icon = CategoryType.entries.firstOrNull { it.type == this.name }?.icon
        ?: CategoryType.FOOD.icon,
    iconType = CategoryType.entries.firstOrNull { it.type == this.name }?.type
        ?: CategoryType.FOOD.type
)

@Parcelize
data class MonthlyTransactionModel(
    val month: String,
    val income: Long,
    val expenses: Long,
) : Parcelable {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MonthlyTransactionModel>() {
            override fun areItemsTheSame(
                oldItem: MonthlyTransactionModel,
                newItem: MonthlyTransactionModel
            ) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MonthlyTransactionModel,
                newItem: MonthlyTransactionModel,
            ) =
                oldItem == newItem
        }
    }
}
