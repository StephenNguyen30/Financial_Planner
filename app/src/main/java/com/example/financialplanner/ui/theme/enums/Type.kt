package com.example.financialplanner.ui.theme.enums

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.financialplanner.R

enum class CategoryType(
    @StringRes
    val titleRes: Int,
    val type: String,
    @DrawableRes
    val icon: Int,
) {
    FOOD(R.string.food, "Food", R.drawable.ic_food),
    TRANSPORT(R.string.transport, "Transport", R.drawable.ic_transport),
    EDUCATION(R.string.education, "Education", R.drawable.ic_education),
    ENTERTAINMENT(R.string.entertainment, "Entertainment", R.drawable.ic_entertainment),
    SOCIAL(R.string.social, "Social", R.drawable.ic_social),
    GIFT(R.string.gift, "Gift", R.drawable.ic_gift),
    CLOTHING(R.string.clothing, "Clothing", R.drawable.ic_clothing),
    PETS(R.string.pets, "Pets", R.drawable.ic_pets),
    HEALTH_CARE(R.string.health_care, "Health care", R.drawable.ic_health_care),
    HOUSEHOLD(R.string.household, "Household", R.drawable.ic_household),

    SALARY(R.string.salary, "Salary", R.drawable.ic_salary),
    PETTY_CASH(R.string.petty_cash, "Petty cash", R.drawable.ic_petty_cash),
    INTEREST(R.string.interest, "Interest", R.drawable.ic_interest),
    INVESTMENT(R.string.investment, "Investment", R.drawable.ic_investment),
    BONUS(R.string.bonus, "Bonus", R.drawable.ic_bonus),
    ALLOWANCE(R.string.allowance, "Allowance", R.drawable.ic_allowance),

    ADD_CATEGORIES(R.string.add_categories, "Add Categories", R.drawable.ic_plus),
}