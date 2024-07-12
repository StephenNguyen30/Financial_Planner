package com.example.financialplanner.ui.theme.base

import android.widget.EditText
import java.util.Locale

fun String.formatAmount(): String {
    if (this.contains("  đ")) return this
    return this.plus("  đ")
}

fun String.emptyString(): String {
    return ""
}


fun String.formatDecimal(): String {
    val cleanInput = this.replace(",", "").replace(".", "")

    val formattedAmount = StringBuilder(cleanInput)
    for (i in formattedAmount.length - 3 downTo 1 step 3) {
        formattedAmount.insert(i, ".")
    }
    return formattedAmount.toString()
}

fun String.titleCase(): String =
    this.lowercase().replaceFirstChar {it.uppercase() }