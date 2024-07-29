package com.example.financialplanner.ui.theme.base

import java.security.MessageDigest
import java.util.Base64


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

fun String.shortenId(): String {
    return try {
        // SHA-256 hash of id
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(this.toByteArray())

        //encode to Base64
        val base64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes)

        //short version of base64 string
        base64Hash.take(16)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}