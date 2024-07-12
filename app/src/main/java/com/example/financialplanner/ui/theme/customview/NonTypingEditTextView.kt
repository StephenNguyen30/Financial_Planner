package com.example.financialplanner.ui.theme.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.findFragment
import com.example.financialplanner.R
import com.example.financialplanner.ui.theme.TransactionsFragment
import com.example.financialplanner.ui.theme.base.disableEditText
import com.example.financialplanner.ui.theme.base.hideKeyboard

class NonTypingEditTextView(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    init {
        this.disableEditText()
        showSoftInputOnFocus = false

        setOnFocusChangeListener { _, hasFocus ->
            val fragment = findFragment<TransactionsFragment>()
            fragment.let {
                if (hasFocus) {
                    it.bindingPopUp(true)
                    context.hideKeyboard(this)
                    val title = when (id) {
                        R.id.etDate -> context.getString(R.string.date)
                        R.id.etCategory -> context.getString(R.string.category)
                        R.id.etAccount -> context.getString(R.string.account)
                        else -> ""
                    }
                    it.setTitlePopUpName(title)
                    it.binding.ivCancel.setOnClickListener {
                        fragment.bindingPopUp(false)
                        clearFocus()
                    }
                    when(id){
                        R.id.etDate -> it.initCalendarRcv()
                        R.id.etAccount -> it.initRecyclerView()
                        R.id.etCategory -> it.initRecyclerView()
                    }

                } else {
                    it.bindingPopUp(false)
                }
            }
        }
    }
}
