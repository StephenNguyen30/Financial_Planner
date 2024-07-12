package com.example.financialplanner.ui.theme.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class CalendarModel(
    val date: LocalDate? = null,
    val isCurrentMonth: Boolean = false,
    val isToday: Boolean = false,
    val isSelected: Boolean = false,
    val dayName: String? = null
) : Parcelable {
    constructor(dayName: String) : this(null, false, false, false, dayName)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CalendarModel>() {
            override fun areItemsTheSame(oldItem: CalendarModel, newItem: CalendarModel) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CalendarModel,
                newItem: CalendarModel,
            ) =
                oldItem == newItem
        }
    }
}