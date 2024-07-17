package com.example.financialplanner.ui.theme.implementation

import com.example.financialplanner.R
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.respository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(): TransactionRepository {
    override suspend fun getTransCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel("Food", R.drawable.ic_food, true),
            CategoryModel("Transport", R.drawable.ic_transport, true),
            CategoryModel("Education", R.drawable.ic_education, true),
            CategoryModel("Entertainment", R.drawable.ic_entertainment, true),
            CategoryModel("Social", R.drawable.ic_social,true),
            CategoryModel("Gift", R.drawable.ic_gift,true),
            CategoryModel("Clothing", R.drawable.ic_clothing, true),
            CategoryModel("Pets", R.drawable.ic_pets, true),
            CategoryModel("Health care", R.drawable.ic_health_care, true),
            CategoryModel("Household", R.drawable.ic_household,true),
            CategoryModel("+", 0, false)
        )
    }

    override suspend fun getTransAccount(): List<CategoryModel> {
        return listOf(
            CategoryModel("Cash", 0, false),
            CategoryModel("Card", 0, false),
            CategoryModel("Bank", 0, false),
            CategoryModel("Add", 0, false)
        )
    }

    override suspend fun getTransIncomeCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel("Salary", R.drawable.ic_salary, true),
            CategoryModel("Petty cash", R.drawable.ic_petty_cash, true),
            CategoryModel("Interest", R.drawable.ic_interest, true),
            CategoryModel("Investment", R.drawable.ic_investment, true),
            CategoryModel("Bonus", R.drawable.ic_bonus,true),
            CategoryModel("Allowance", R.drawable.ic_allowance,true),
            CategoryModel("Add", 0, false)
        )
    }
}