package com.example.financialplanner.ui.theme.usecases

import com.example.financialplanner.ui.theme.base.DefaultDispatcher
import com.example.financialplanner.ui.theme.base.IoDispatcher
import com.example.financialplanner.ui.theme.model.MonthlyTransactionModel
import com.example.financialplanner.ui.theme.model.TransactionModel
import com.example.financialplanner.ui.theme.respository.FirebaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetTransMonthlyUseCase @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userId: String) = withContext(ioDispatcher) {
        firebaseRepository.getTransactionsById(userId).first().toModel()
    }

    private suspend fun List<TransactionModel>.toModel() = withContext(defaultDispatcher){
        val monthlyTotal = calculateMonthlyTotals(this@toModel)
        monthlyTotal
    }

    private fun calculateMonthlyTotals(transactions: List<TransactionModel>): List<MonthlyTransactionModel> {
        val groupedTransactions = transactions.groupBy { getMonthFromDate(it.dayDate) }
        val monthlyTotals = mutableListOf<MonthlyTransactionModel>()
        for ((month, transactionsInMonth) in groupedTransactions) {
            var income = 0L
            var expenses = 0L
            for (transaction in transactionsInMonth) {
                val amount = transaction.amount.toLongOrNull() ?: 0L
                if (transaction.type == "Income") {
                    income += amount
                } else if (transaction.type == "Expense") {
                    expenses += amount
                }
            }
            monthlyTotals.add(MonthlyTransactionModel(month, income, expenses))
        }

        return monthlyTotals
    }
    private fun getMonthFromDate(dateString: String): String {
        return dateString.substring(3, 5)
    }
}