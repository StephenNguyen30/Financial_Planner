package com.example.financialplanner.ui.theme.base

import androidx.navigation.NavController

fun NavController.isFragmentInBackStack(destinationId: Int) =
    try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }