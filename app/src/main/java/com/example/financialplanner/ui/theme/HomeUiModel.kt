package com.example.financialplanner.ui.theme

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.PlanModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class HomeUiModel(
    val type: Int = -1,
    val plan: PlanModel?,
    val date: LocalDate,
    val planType: List<PlanModel>?,
    val totalBudget: Long = 0,
    val category: List<CategoryModel>?,
    val isCategory: Boolean = false,

    ): Parcelable{
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeUiModel>() {
            override fun areItemsTheSame(oldItem: HomeUiModel, newItem: HomeUiModel) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: HomeUiModel,
                newItem: HomeUiModel,
            ) =
                oldItem == newItem
        }
    }
}
