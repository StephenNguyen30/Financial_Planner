package com.example.financialplanner.ui.theme.respository


import com.example.financialplanner.ui.theme.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: UserModel)
    suspend fun getUserByIdToken(id: String): Flow<UserModel>
}


