package com.example.financialplanner.ui.theme.respository

import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.TransactionModel
import com.example.financialplanner.ui.theme.model.UserModel
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun addUser(user: UserModel)
    suspend fun addTransaction(userId: String, transaction: TransactionModel)
    suspend fun addCategories(userId: String, transactionId: String, categories: List<CategoryModel>)
    suspend fun addAccounts(userId: String, accounts: List<CategoryModel>)
    suspend fun getUserById(userId: String): Flow<UserModel?>
    suspend fun getTransactionsById(userId: String): Flow<List<TransactionModel>>
}