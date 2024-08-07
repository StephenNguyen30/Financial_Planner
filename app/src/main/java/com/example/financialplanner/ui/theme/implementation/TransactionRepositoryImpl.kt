package com.example.financialplanner.ui.theme.implementation

import com.example.financialplanner.R
import com.example.financialplanner.ui.theme.enums.CategoryType
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.respository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor() : TransactionRepository {
    override suspend fun getTransCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel("Food", CategoryType.FOOD.icon),
            CategoryModel("Transport", CategoryType.TRANSPORT.icon),
            CategoryModel("Education", CategoryType.EDUCATION.icon),
            CategoryModel("Entertainment", CategoryType.ENTERTAINMENT.icon),
            CategoryModel("Social", CategoryType.SOCIAL.icon),
            CategoryModel("Gift", CategoryType.GIFT.icon),
            CategoryModel("Clothing", CategoryType.CLOTHING.icon),
            CategoryModel("Pets", CategoryType.PETS.icon),
            CategoryModel("Health care", CategoryType.HEALTH_CARE.icon),
            CategoryModel("Household", CategoryType.HOUSEHOLD.icon),
            CategoryModel("Add Categories", CategoryType.ADD_CATEGORIES.icon, false)
        )
    }

    override suspend fun getTransAccount(): List<CategoryModel> {
        return listOf(
            CategoryModel("Cash", 0),
            CategoryModel("Card", 0),
            CategoryModel("Bank", 0),
            CategoryModel("Add Accounts", 0, false)
        )
    }

    override suspend fun getTransIncomeCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel("Salary", R.drawable.ic_salary),
            CategoryModel("Petty cash", R.drawable.ic_petty_cash),
            CategoryModel("Interest", R.drawable.ic_interest),
            CategoryModel("Investment", R.drawable.ic_investment),
            CategoryModel("Bonus", R.drawable.ic_bonus),
            CategoryModel("Allowance", R.drawable.ic_allowance),
            CategoryModel("Add Categories", 0, false)
        )
    }
}