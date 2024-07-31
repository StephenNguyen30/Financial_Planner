package com.example.financialplanner.ui.theme.respository

import com.example.financialplanner.R
import com.example.financialplanner.ui.theme.model.CategoryModel
import dagger.Provides


interface TransactionRepository {
    suspend fun getTransCategories(): List<CategoryModel>
    suspend fun getTransAccount(): List<CategoryModel>
    suspend fun getTransIncomeCategories(): List<CategoryModel>
}