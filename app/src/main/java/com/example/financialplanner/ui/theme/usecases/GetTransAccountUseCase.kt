package com.example.financialplanner.ui.theme.usecases

import com.example.financialplanner.ui.theme.base.IoDispatcher
import com.example.financialplanner.ui.theme.respository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTransAccountUseCase @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        transactionRepository.getTransAccount()
    }
}