package com.example.financialplanner.ui.theme.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: String = "",
    val imageUrl: String? = "",
    val phoneNumber: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String = "",
    val token: String = ""
): Parcelable
