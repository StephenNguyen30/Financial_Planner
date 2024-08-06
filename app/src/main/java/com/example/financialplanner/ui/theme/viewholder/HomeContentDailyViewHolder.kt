package com.example.financialplanner.ui.theme.viewholder

import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.financialplanner.R
import com.example.financialplanner.databinding.ItemHomeContentDailyBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import com.example.financialplanner.ui.theme.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeContentDailyViewHolder(
    parent: ViewGroup,
    private val positionOnClick: (Int) -> Unit = {}
) : BaseBindingViewHolder<TransactionModel, ItemHomeContentDailyBinding>(parent get ItemHomeContentDailyBinding::inflate) {

    override fun bind(data: TransactionModel) {
        val category = data.categories
        val account = data.accounts
        binding.ivIcon.setImageResource(category.icon)
        Log.d("Firebase", "bind:${data.amount} ${category.icon}")

        val date = data.dayDate.substring(0, 2)
        binding.tvDate.text = date
        binding.tvCategory.text = category.name
        binding.tvAmount.text = data.amount
        binding.tvAccount.text = account.name
        binding.tvNote.text = data.note
        val dayOfWeek = getDayOfWeek(data.dayDate)
        binding.tvDateByWeek.text = dayOfWeek
        if (data.type == "expenses") {
            binding.tvAmount.setTextColor(getColor(itemView.context, R.color.orange_FC8800))
        } else if (data.type == "income") {
            binding.tvAmount.setTextColor(getColor(itemView.context, R.color.green))
        }
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                positionOnClick.invoke(position)
            }
        }
    }

    private fun getDayOfWeek(dateString : String): String{
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val date = format.parse(dateString) ?: return ""
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri","Sat")
        return daysOfWeek[dayOfWeek - 1]
    }
}