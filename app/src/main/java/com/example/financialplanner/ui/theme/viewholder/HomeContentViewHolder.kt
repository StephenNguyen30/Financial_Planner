package com.example.financialplanner.ui.theme.viewholder

import android.graphics.Color
import android.view.ViewGroup
import com.example.financialplanner.databinding.ItemHomeContentBinding
import com.example.financialplanner.ui.theme.base.adapter.BaseBindingViewHolder
import com.example.financialplanner.ui.theme.base.get
import com.example.financialplanner.ui.theme.model.TransactionModel
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialplanner.R

class HomeContentViewHolder(
    parent: ViewGroup,
    private val positionOnClick: (Int) -> Unit = {}
) : BaseBindingViewHolder<TransactionModel, ItemHomeContentBinding>(parent get ItemHomeContentBinding::inflate) {
    private var isNoteVisible = false

    override fun bind(data: TransactionModel) {
        val category = data.categories
        val account = data.accounts
        binding.ivIcon.setImageResource(category.icon)
        val date = data.dayDate.substring(0, 2)
        binding.tvDate.text = date
        binding.tvCategory.text = category.name
        binding.tvAmount.text = data.amount
        binding.tvAccount.text = account.name
        binding.tvNote.text = data.note
        if (data.type == "expenses") {
            binding.tvAmount.setTextColor(getColor(itemView.context, R.color.orange_FC8800))
        } else if (data.type == "income") {
            binding.tvAmount.setTextColor(getColor(itemView.context, R.color.green))
        }
        binding.root.setOnClickListener {
            isNoteVisible = !isNoteVisible

            binding.tvNote.isVisible = isNoteVisible
            binding.tvNoteTitle.isVisible = isNoteVisible
            binding.tvLine1.isVisible = isNoteVisible

            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                positionOnClick.invoke(position)
            }
        }
    }
}