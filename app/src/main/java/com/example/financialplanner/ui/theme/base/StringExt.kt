package com.example.financialplanner.ui.theme.base


fun String.formatAmount(): String {
    if (this.isEmpty()) return ""

    val parts = this.split(" ")
    val amount = parts[0].replace(".", "")

    val formattedAmount = StringBuilder()
    val reversedAmount = amount.reversed()
    for (i in reversedAmount.indices) {
        formattedAmount.append(reversedAmount[i])
        if ((i + 1) % 3 == 0 && (i + 1) < reversedAmount.length) {
            formattedAmount.append(".")
        }
    }

    return formattedAmount.reverse().toString() + "  đ"
}

fun String.emptyString(): String {
    return ""
}


fun String.titleCase(): String =
    this.lowercase().replaceFirstChar { it.uppercase() }