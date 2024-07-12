package com.example.financialplanner.ui.theme.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatEditText

import androidx.fragment.app.findFragment

import com.example.financialplanner.ui.theme.TransactionsFragment
import com.example.financialplanner.ui.theme.base.setTextAmount

class NonEditableEditView(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    private val setTextCallback: (String) -> Unit = {
        findFragment<TransactionsFragment>().binding.etAmount.setTextAmount(it)
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !s.toString().contains("  đ"))
                    setTextCallback.invoke(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        val textLength = text?.length ?: 0
        if (textLength >= 4)
            setSelection(textLength - 3)
        else if (textLength == 3)
            text?.clear()
        else
            setSelection(0)
    }
}