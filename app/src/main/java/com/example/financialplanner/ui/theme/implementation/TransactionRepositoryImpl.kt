package com.example.financialplanner.ui.theme.implementation

import com.example.financialplanner.R
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.respository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(): TransactionRepository {
    override suspend fun getTransCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel("Food", R.drawable.ic_food),
            CategoryModel("Transport", R.drawable.ic_transport),
            CategoryModel("Education", R.drawable.ic_education),
            CategoryModel("Entertainment", R.drawable.ic_entertainment),
            CategoryModel("Social", R.drawable.ic_social),
            CategoryModel("Gift", R.drawable.ic_gift),
            CategoryModel("Clothing", R.drawable.ic_clothing),
            CategoryModel("Pets", R.drawable.ic_pets),
            CategoryModel("Health care", R.drawable.ic_health_care),
            CategoryModel("Household", R.drawable.ic_household),
            CategoryModel("", R.drawable.ic_plus)
        )
    }

    override suspend fun getTransAccount(): List<CategoryModel> {
        return listOf(
            CategoryModel("Cash", 0),
            CategoryModel("Card", 0),
            CategoryModel("Bank", 0),
            CategoryModel("Add", 0)
        )
    }
}