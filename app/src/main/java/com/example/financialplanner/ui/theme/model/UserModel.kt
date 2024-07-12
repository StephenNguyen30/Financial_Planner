package com.example.financialplanner.ui.theme.model


data class UserModel(
    val id: String,
    val displayName: String? = null,
    val imageUrl: String? = null,
    val phoneNumber: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null
)
