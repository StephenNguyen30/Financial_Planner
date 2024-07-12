package com.example.financialplanner.ui.theme.base

import java.io.IOException

class NoInternetException : IOException(MESSAGE) {
    companion object {
        const val MESSAGE = "No internet connect"
    }
}
