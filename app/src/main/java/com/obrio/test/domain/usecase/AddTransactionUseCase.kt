package com.obrio.test.domain.usecase

import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.repository.FinanceRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val financeRepository: FinanceRepository
){
    suspend operator fun invoke(transaction: Transaction) {
        financeRepository.addTransaction(transaction)
    }
}