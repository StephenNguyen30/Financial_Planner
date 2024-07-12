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
    val dayDate: Date,
    val category: CategoryModel,
    val income: Long = 0,
    val expenses: Long = 0,
    val note: String = ""
) : Parcelable

@Parcelize
data class CategoryModel(
    val name: String,
    val icon: Int,
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
