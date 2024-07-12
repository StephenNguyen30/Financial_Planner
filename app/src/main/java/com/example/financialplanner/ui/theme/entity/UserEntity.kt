package com.example.financialplanner.ui.theme.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financialplanner.ui.theme.entity.UserEntity.Companion.USERS


@Entity(tableName = USERS)
data class UserEntity(
    @PrimaryKey val id: String,
    val displayName: String?,
    val firstName: String?,
    val familyName: String?,
    val phoneNumber: String?,
    val imageUrl: String?,
) {
    companion object {
        const val USERS = "users"
    }
}
